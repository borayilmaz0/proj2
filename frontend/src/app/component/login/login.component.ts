import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UserLoginRequest } from 'src/app/request/user-login-request';
import { AuthService } from 'src/app/service/auth.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  userLoginRequest: UserLoginRequest;

  constructor(private route: ActivatedRoute, 
    private router: Router, 
    private authService: AuthService,
    private tokenService: TokenService) 
  {
    this.userLoginRequest = new UserLoginRequest;
  }

  onSubmit() {
    try {
      let token: string = "";
      this.authService.login(this.userLoginRequest).subscribe(
        loginRes => {
          localStorage.setItem("userId", loginRes.userId.toString());
          loginRes.token;
          this.gotoUserDashboard();
        },
        error => {
          window.location.reload();
        }
      );
    } catch (error) {
      console.log(error);
      window.location.reload();
    }
  }

  gotoUserDashboard() {
    
    this.router.navigate(['/dashboard']); 
  }

  ngOnInit()
  {
    localStorage.clear();
  }

}
