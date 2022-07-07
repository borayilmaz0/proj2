import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/login/login.component';
import { SignUpComponent } from './component/sign-up/sign-up.component';
import { UserService } from './service/user.service';
import { UserListComponent } from './component/user-list/user-list.component';
import { UserDashboardComponent } from './component/user-dashboard/user-dashboard.component';
import { EventFormComponent } from './component/event-form/event-form.component';
import { EventDetailsComponent } from './component/event-details/event-details.component';
import { InviteFormComponent } from './component/invite-form/invite-form.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    UserListComponent,
    UserDashboardComponent,
    EventFormComponent,
    EventDetailsComponent,
    InviteFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
