import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UserRegisterRequest } from 'src/app/request/user-register-request';
import { AuthService } from 'src/app/service/auth.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  userRegisterRequest : UserRegisterRequest;

  constructor(private route: ActivatedRoute, 
    private router: Router, 
    private authService: AuthService,
    private userService: UserService) 
  {
    this.userRegisterRequest = new UserRegisterRequest();
  }

  onSubmit() {
    this.authService.register(this.userRegisterRequest).subscribe(
      r => this.gotoLogin()
    );

    // this.userService.save(this.user).subscribe(() => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }

  gotoLogin() {
    this.router.navigate(['/login']);
  }

  ngOnInit() {
    
  }

}
