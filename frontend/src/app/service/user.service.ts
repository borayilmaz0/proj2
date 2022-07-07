import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs/internal/Observable';
import { UserLoginRequest } from '../request/user-login-request';
import { TokenService } from './token.service';
import { MailResponse } from '../response/mail-response';
import { switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;
  private mailsUrl: string;

  constructor(private http: HttpClient,
    private tokenService: TokenService) 
  {
    this.usersUrl = 'http://localhost:8080/users';
    this.mailsUrl = 'http://localhost:8080/mails';
  }

  public findAll(): Observable<User[]> {
    //return this.http.get<User[]>(this.usersUrl, tokenService.);
    return this.getUserToken().pipe(switchMap(r => { 
      return this.http.get<User[]>(this.usersUrl, {headers:{Authorization: "Bearer " + r.latestToken}})
    }))
  }

  public findUserbyId(id: number): Observable<User> {
    return this.getUserToken().pipe(switchMap(r => { 
      return this.http.get<User>(this.usersUrl + "/" + id, {headers:{Authorization: "Bearer " + r.latestToken}})
    }))
  }

  public findAllOtherThan(id: number) {
    return this.getUserToken().pipe(switchMap(r => { 
      return this.http.get<User[]>(this.usersUrl + "/idIsNot/" + id, {headers:{Authorization: "Bearer " + r.latestToken}})
    }))
  }

  findAllInvitedUsers(eventId: number)
  {
    let mails: MailResponse[] = [];
    let invitedUsers: User[] = [];

    this.getUserToken().pipe(switchMap(r => { 
      return this.http.get<MailResponse[]>(this.mailsUrl + "/event/" + eventId, {headers:{Authorization: "Bearer " + r.latestToken}})
    })).subscribe(result => {
      mails = result;
      
      for (let i = 0; i < mails.length; i++) {
        this.findUserbyId(mails[i].invitedUserId).subscribe(result => invitedUsers.push(result));
      }
    });
    
    return invitedUsers;
  }
  
  public save(user: User): Observable<string> {
    return this.getUserToken().pipe(switchMap(
      r => { return this.http.post<string>(this.usersUrl, user, {headers:{Authorization: "Bearer " + r.latestToken}})})
    )
  }

  private getUserToken()
  {
    return this.tokenService.getTokenByUserId();
  }
}
