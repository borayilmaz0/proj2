import { HttpClient, HttpResponse, HttpStatusCode } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { User } from '../model/user';
import { UserLoginRequest } from '../request/user-login-request';
import { UserRegisterRequest } from '../request/user-register-request';
import { UserLoginResponse } from '../response/user-login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl: string;

  constructor(private http: HttpClient) {
    this.authUrl = 'http://localhost:8080/auth';
  }

  public login(userLoginRequest: UserLoginRequest)
  {
    return this.http.post<UserLoginResponse>(this.authUrl + "/login", userLoginRequest);
  }

  public register(userRegisterRequest: UserRegisterRequest): Observable<number> {
    return this.http.post<number>(this.authUrl + "/register", userRegisterRequest);
  }
}
