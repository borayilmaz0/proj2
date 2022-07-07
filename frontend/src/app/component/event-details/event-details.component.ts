import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { EventResponse } from 'src/app/response/event-response';
import { EventService } from 'src/app/service/event.service';
import { TokenService } from 'src/app/service/token.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {

  eventResponse: EventResponse;
  invitedUsers: User[];

  constructor(private route: ActivatedRoute, 
    private router: Router,
    private tokenService: TokenService,
    private userService: UserService,
    private eventService: EventService) 
  { 
    this.eventResponse = new EventResponse();
    this.invitedUsers = [];
  }

  canModify() {
    let idStr = localStorage.getItem("userId");
    let userId = idStr == null ? -1 : parseInt(idStr);

    return userId == this.eventResponse.adminid;
  }

  onUpdateUsers(eventId: number) {
    localStorage.setItem("eventId", eventId.toString());
    this.router.navigate(["/inviteUsers"]);
  }

  onDelete(eventId: number) {
    localStorage.setItem("eventId", eventId.toString());
    this.eventService.deleteEvent(eventId).subscribe(() => window.location.reload());
    
  }

  getIsMonthly()
  {
    return this.eventResponse.isMonthly ? "Yes" : "No";
  }

  ngOnInit(): void {
    try {
      let idStr = localStorage.getItem("userId");
      let userId = idStr == null ? -1 : parseInt(idStr);
      if (userId === -1)
        this.router.navigate(["/login"]);
      
      idStr = localStorage.getItem("eventId");
      let eventId = idStr == null ? -1 : parseInt(idStr);

      this.eventService.findById(eventId).subscribe(result => {
        this.eventResponse = result;});
      
      
      this.invitedUsers = this.userService.findAllInvitedUsers(eventId);
      
    } catch(error) {
      console.log(error);
      this.router.navigate(["/login"]);
    }
  }

}
