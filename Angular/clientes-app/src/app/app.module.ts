import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DirectiveComponent } from './directive/directive.component';
import { CustomersComponent } from './customers/customers.component';
import { PaginatorComponent } from './paginator/paginator.component';
import { FormComponent } from './customers/form.component';

import { CustomerService } from './customers/customer.service';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { DetailComponent } from './customers/detail/detail.component';
import { LoginComponent } from './users/login.component';
import { AuthGuard } from './users/guards/auth.guard';
import { RoleGuard } from './users/guards/role.guard';
import { TokenInterceptor } from './users/interceptors/token.interceptor';
import { AuthInterceptor } from './users/interceptors/auth.interceptor';
import { InvoiceDetailComponent } from './invoices/invoice-detail.component';
import { InvoicesComponent } from './invoices/invoices.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

const routes: Routes = [
  { path: '', redirectTo: '/customers', pathMatch: 'full' },
  { path: 'directives', component: DirectiveComponent },
  { path: 'customers', component: CustomersComponent },
  { path: 'customers/page/:page', component: CustomersComponent },
  { path: 'customers/form', component: FormComponent, canActivate: [RoleGuard, AuthGuard], data: { role: 'ROLE_ADMIN' } },
  { path: 'customers/form/:id', component: FormComponent, canActivate: [RoleGuard, AuthGuard], data: { role: 'ROLE_ADMIN' } },
  { path: 'login', component: LoginComponent },
  { path: 'invoices/form/:customerId', component: InvoicesComponent, canActivate: [RoleGuard, AuthGuard], data: { role: 'ROLE_ADMIN' } },
  { path: 'invoices/:id', component: InvoiceDetailComponent, canActivate: [RoleGuard, AuthGuard], data: { role: 'ROLE_USER' } }
]

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    DirectiveComponent,
    CustomersComponent,
    FormComponent,
    PaginatorComponent,
    DetailComponent,
    LoginComponent,
    InvoiceDetailComponent,
    InvoicesComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatAutocompleteModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule
  ],
  providers: [CustomerService,
    { provide: LOCALE_ID, useValue: 'en-US' },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
