import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventCreateRequest } from 'src/app/request/event-create-request';
import { EventService } from 'src/app/service/event.service';
import { TokenService } from 'src/app/service/token.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.css']
})
export class EventFormComponent implements OnInit {

  eventCreateRequest: EventCreateRequest;
  isMonthly: boolean;

  constructor(private route: ActivatedRoute, 
      private router: Router,
      private tokenService: TokenService,
      private userService: UserService,
      private eventService: EventService) 
  { 
    this.eventCreateRequest = new EventCreateRequest();
    let idStr = localStorage.getItem("userId");
    let userId = idStr == null ? -1 : parseInt(idStr);
    this.eventCreateRequest.adminid = userId;
    this.isMonthly = false;
  }

  onSubmit() {
    this.eventService.postEvent(this.eventCreateRequest).subscribe(
      r => {
        if (r !== null)
          this.gotoDashboard(); 
      }
    );
  }

  onMonthly()
  {
    this.eventCreateRequest.monthly = !this.eventCreateRequest.monthly;
  }

  gotoDashboard() {
    this.router.navigate(["/dashboard"]);
  }

  ngOnInit(): void {
      let idStr = localStorage.getItem("userId");
      let userId = idStr == null ? -1 : parseInt(idStr);
      if (userId === -1)
        this.router.navigate(["/login"]);
    
    let date = new Date();
  }

}
