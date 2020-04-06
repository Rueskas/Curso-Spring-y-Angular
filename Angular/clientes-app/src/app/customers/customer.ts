export class Customer {

  constructor(private id?: number, private name?: string, private surname?: string,
    private email?: string, private createdAt?: string, private avatar?: string) {
  }


  public getId(): number {
    return this.id;
  }

  public getName(): string {
    return this.name;
  }

  public getSurname(): string {
    return this.surname;
  }

  public getEmail(): string {
    return this.email;
  }

  public getCreatedAt(): string {
    return this.createdAt;
  }

  public getAvatar(): string {
    return this.avatar;
  }

  public setId(id: number): void {
    this.id = id;
  }

  public setName(name: string): void {
    this.name = name;
  }
  public setSurname(surname: string): void {
    this.surname = surname;
  }
  public setEmail(email: string): void {
    this.email = email;
  }
  public setCreatedAt(createdAt: string): void {
    this.createdAt = createdAt;
  }
  public setAvatar(avatar: string): void {
    this.avatar = avatar;
  }
}
