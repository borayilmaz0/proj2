import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { Event } from 'src/app/model/event';
import { EventService } from 'src/app/service/event.service';
import { TokenService } from 'src/app/service/token.service';
import { UserService } from 'src/app/service/user.service';
import { EventResponse } from 'src/app/response/event-response';
import { MailService } from 'src/app/service/mail.service';
import { MailResponse } from 'src/app/response/mail-response';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})

export class UserDashboardComponent {

  user: User;
  allEvents: EventResponse[] = [];
  events: EventResponse[] = [];
  userMails: MailResponse[] = [];
  userEvents: EventResponse[] = [];


  constructor(private route: ActivatedRoute, 
    private router: Router,
    private tokenService: TokenService,
    private userService: UserService,
    private eventService: EventService,
    private mailService: MailService) { 
      this.user = new User;
  }
  
  onEventDetails(eventId: number)
  {
    localStorage.setItem("eventId", eventId.toString());
    this.gotoEventDetails();
  }

  onUpdateUsers(eventId: number) {
    localStorage.setItem("eventId", eventId.toString());
    this.gotoModifyUsers();
  }

  onDelete(eventId: number) {
    localStorage.setItem("eventId", eventId.toString());
    this.eventService.deleteEvent(eventId).subscribe(() => window.location.reload());
    
  }

  gotoEventDetails()
  {
    this.router.navigate(["/eventDetails"]);
  }

  gotoModifyUsers()
  {
    this.router.navigate(["/inviteUsers"])
  }

  ngOnInit() {
    try {
      localStorage.removeItem("eventId");
      let idStr = localStorage.getItem("userId");
      let userId = idStr == null ? -1 : parseInt(idStr);
      if (userId === -1)
        this.router.navigate(["/login"]);
      
      this.userService.findUserbyId(userId).subscribe(r => {this.user = r});

      this.eventService.findRelatedEvents(userId).subscribe(r => {this.events = r});
      
      this.eventService.findAllByUserId(userId).subscribe(data => {
        if (data !== []) {
          this.userEvents = data;
        }
      });

       

    } catch(error) {
      console.log(error);
      this.router.navigate(["/login"]);
    }

  }

}
