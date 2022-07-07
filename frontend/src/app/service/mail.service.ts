import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { switchMap } from "rxjs";
import { MailCreateRequest } from "../request/mail-create-request";
import { MailResponse } from "../response/mail-response";
import { TokenService } from "./token.service";


@Injectable({
  providedIn: 'root'
})
export class MailService {

  private mailsUrl: string;

  constructor(private http: HttpClient,
      private tokenService: TokenService) 
  {
    this.mailsUrl = 'http://localhost:8080/mails';
  }

  findAll() {
    return this.getUserToken().pipe(switchMap(r => { 
      return this.http.get<MailResponse[]>(this.mailsUrl, {headers:{Authorization: "Bearer " + r.latestToken}})
    }))
  }

  findAllMailsByEventId(eventId: number) {
    return this.getUserToken().pipe(switchMap(r => { 
      return this.http.get<MailResponse[]>(this.mailsUrl + "/event/" + eventId, {headers:{Authorization: "Bearer " + r.latestToken}})
    }))
  }

  addMail(mailCreateRequest: MailCreateRequest)
  {
    return this.getUserToken().pipe(switchMap(r => { 
      return this.http.post<MailResponse>(this.mailsUrl, mailCreateRequest, {headers:{Authorization: "Bearer " + r.latestToken}})
    }))
  }

  deleteMail(mailId: number)
  {
    return this.getUserToken().pipe(switchMap(r => { 
      return this.http.delete<void>(this.mailsUrl + "/" + mailId, {headers:{Authorization: "Bearer " + r.latestToken}})
    }))
  }

  getAllRelatedMails(userId: number)
  {
    return this.getUserToken().pipe(switchMap(r => { 
      return this.http.get<MailResponse[]>(this.mailsUrl + "/getRaletedMails/" + userId, {headers:{Authorization: "Bearer " + r.latestToken}})
    }))
  }

  private getUserToken()
  {
    return this.tokenService.getTokenByUserId();
  }
}