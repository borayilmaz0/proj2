import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventDetailsComponent } from './component/event-details/event-details.component';
import { EventFormComponent } from './component/event-form/event-form.component';
import { InviteFormComponent } from './component/invite-form/invite-form.component';
import { LoginComponent } from './component/login/login.component';
import { SignUpComponent } from './component/sign-up/sign-up.component';
import { UserDashboardComponent } from './component/user-dashboard/user-dashboard.component';
import { UserListComponent } from './component/user-list/user-list.component';


const routes: Routes = [
  { path: "users", component: UserListComponent },
  { path: "register", component: SignUpComponent },
  { path: "login", component: LoginComponent },
  { path: "dashboard", component: UserDashboardComponent },
  { path: "postevent", component: EventFormComponent },
  { path: "eventDetails", component: EventDetailsComponent },
  { path: "inviteUsers", component: InviteFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
