import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { User } from 'src/app/model/user';
import { MailCreateRequest } from 'src/app/request/mail-create-request';
import { EventResponse } from 'src/app/response/event-response';
import { MailResponse } from 'src/app/response/mail-response';
import { UserResponse } from 'src/app/response/user-response';
import { EventService } from 'src/app/service/event.service';
import { MailService } from 'src/app/service/mail.service';
import { TokenService } from 'src/app/service/token.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-invite-form',
  templateUrl: './invite-form.component.html',
  styleUrls: ['./invite-form.component.css']
})
export class InviteFormComponent implements OnInit {

  users: User[] = [];
  invitedUsers: number[] = [];
  userId: number = -1;
  events: EventResponse[] = [];
  eventResponse: EventResponse;
  mails: MailResponse[] = [];
  userInvitations: Map<number, boolean>;
  modifiedInvitations: Map<number, boolean>;
  isEventSelected: boolean = false;

  constructor(private route: ActivatedRoute, 
      private router: Router,
      private tokenService: TokenService,
      private userService: UserService,
      private eventService: EventService,
      private mailService: MailService) {
    this.eventResponse = new EventResponse();
    this.userInvitations = new Map<number, boolean>();
    this.modifiedInvitations = new Map<number, boolean>();
  }

  onModifyInvitations()
  {
    if (!this.isEventSelected)
    {
      return;
    }

    for (let i = 0; i < this.users.length; i++) {
      let isInvited = this.modifiedInvitations.get(this.users[i].id);
      if (this.userInvitations.get(this.users[i].id) !== isInvited)
      {
        if (isInvited) 
        {
          this.mailService.addMail(
            new MailCreateRequest(
              "", 
              this.eventResponse.adminid, 
              this.users[i].id, 
              this.eventResponse.id)).subscribe();
        }
        else
        {
          this.mailService.deleteMail(this.getMailIdByInvitedUserId(this.users[i].id)).subscribe();
        }
      }
        
    }

    this.router.navigate(["/dashboard"]);
  }

  private getMailIdByInvitedUserId(userId: number)
  {
    for (let i = 0; i < this.mails.length; i++) {
      if (this.mails[i].adminId === this.eventResponse.adminid
          && this.mails[i].eventId === this.eventResponse.id
          && this.mails[i].invitedUserId === userId)
        return this.mails[i].id;
    }
    return -1;
  }

  onButton(eventId: number)
  {
    this.eventService.findById(eventId).subscribe(result => {this.eventResponse = result; console.log(this.eventResponse.id)});

    this.mailService.findAllMailsByEventId(eventId).pipe( 
      switchMap(result => {
        this.mails = result;
        return this.userService.findAllOtherThan(this.userId)})
    ).subscribe(result => {
        this.users = result

      this.invitedUsers = [];
      for (let i = 0; i < this.mails.length; i++) {
        this.invitedUsers.push(this.mails[i].invitedUserId);
      }
      for (let i = 0; i < this.users.length; i++) {
        this.userInvitations.set(this.users[i].id, this.invitedUsers.includes(this.users[i].id));
        this.modifiedInvitations.set(this.users[i].id, this.invitedUsers.includes(this.users[i].id));
      }
    });
    this.isEventSelected = true;
  }

  ngOnInit(): void {
    try {
      let idStr = localStorage.getItem("userId");
      this.userId = idStr == null ? -1 : parseInt(idStr);
      
      if (this.userId === -1) {
        this.router.navigate(["/login"]);
      }

      this.eventService.findAllByUserId(this.userId).subscribe(r => this.events = r);

      // this.eventService.findById(eventId).subscribe(result => this.eventResponse = result);

      // this.mailService.findAllMailsByEventId(eventId).pipe( 
      //   switchMap(result => {
      //     this.mails = result;
         this.userService.findAllOtherThan(this.userId).subscribe(result => {
          this.users = result

        // for (let i = 0; i < this.mails.length; i++) {
        //   this.invitedUsers.push(this.mails[i].invitedUserId);
        // }
        // for (let i = 0; i < this.users.length; i++) {
        //   this.userInvitations.set(this.users[i].id, this.invitedUsers.includes(this.users[i].id));
        //   this.modifiedInvitations.set(this.users[i].id, this.invitedUsers.includes(this.users[i].id));
        // }
      });
      
    } catch (error) {
      this.router.navigate(["/login"]);
    }
  }
}
