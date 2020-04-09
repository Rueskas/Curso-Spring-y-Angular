import { Component, OnInit } from '@angular/core';
import { Global } from '../global';
import * as  SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { Message } from './models/message';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  private _client: Client;
  private _url = Global.URL;
  public message: Message = new Message();
  public messages: Message[] = [];
  public writting: String;
  connected: boolean = false;
  constructor() { }

  ngOnInit(): void {
    this._client = new Client();

    //Donde se tiene que conectar
    this._client.webSocketFactory = () => {
      return new SockJS(this._url + "chat-websocket");
    }

    //Hacer algo cuando se conecta
    this._client.onConnect = (frame) => {
      this.connected = true;
      console.log("Connected: " + this._client.connected + ": " + frame);

      //Escucha mensajes que envia el servidor
      this._client.subscribe('/chat/message', e => {
        let message = JSON.parse(e.body) as Message;

        if (!this.message.color && message.type == 'NEW_USER' && this.message.username == message.username) {
          this.message.color = message.color;
        }
        message.date = new Date(message.date);
        this.messages.push(message);
      });

      //Escucha cuando alguien esta escribiendo
      this._client.subscribe('/chat/writting', e => {
        this.writting = JSON.parse(e.body);
        console.log(e.body);
        setTimeout(() => this.writting = '', 1500);
      })
      //Avisa de nueva conexion
      this.message.type = 'NEW_USER';
      this._client.publish({ destination: '/app/message', body: JSON.stringify(this.message) })

    }
    this._client.onDisconnect = (frame) => {
      this.connected = false;

    }

  }

  public connect() {
    this._client.activate();
  }
  public disconnect() {

    //Avisa de que se cierra conexion
    this.message.type = 'DISCONNECT_USER';
    this._client.publish({ destination: '/app/message', body: JSON.stringify(this.message) })
    this._client.unsubscribe('/app/message');
    this._client.deactivate();
    this.messages = [];
  }

  public sendMessage(): void {
    //Envia mensaje al servidor
    this.message.type = 'MESSAGE';
    this._client.publish({ destination: '/app/message', body: JSON.stringify(this.message) });
    this.message.text = '';
  }

  public getClassBackground(message: Message) {
    if (message.type == 'NEW_USER') {
      return 'list-group-item-success';
    } else if (message.type == 'DISCONNECT_USER') {
      return 'list-group-item-danger';
    } else {
      return 'list-group-item-light';
    }
  }

  public isWritting() {
    this._client.publish({ destination: '/app/writting', body: JSON.stringify(this.message.username) });
  }
}
