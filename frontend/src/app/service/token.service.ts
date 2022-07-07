import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { UserJwtToken } from "../model/user-jwt-token";


@Injectable({
    providedIn: 'root'
})
export class TokenService 
{
    private tokensUrl: string;
  
    constructor(private http: HttpClient) {
        this.tokensUrl = 'http://localhost:8080/tokens';
    }
    
    getTokenByUserId()
    {
        let str = localStorage.getItem("userId");
        let userId = str === null ? -1 : parseInt(str);

        return this.http.get<UserJwtToken>(this.tokensUrl + "/" + userId);
    }



    provideToken(token: UserJwtToken): Observable<UserJwtToken>
    {
        return this.http.post<UserJwtToken>(this.tokensUrl, token);
    }

    isUserTokenValid(userId: number): boolean
    {
        try {
            let val: boolean = false;
            this.http.get<boolean>(this.tokensUrl + "/validate/" + userId).subscribe(result => val = result);
            return val;
        } catch (error) {
            console.log(error);
            return false;
        }
        
    }

    setAuthoriztion(token: UserJwtToken)
    {
        return new Headers({Authorization: "Bearer " + token.latestToken});
    }

    stringifyToken(token: Observable<UserJwtToken>)
    {
        let result: string = "";
        if (token == undefined) return "";
        token.subscribe(r => result = r.latestToken);
        return result;
    }
}
