import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { TokenService } from 'src/app/service/token.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  
  users: User[] = [];
  constructor(private userService: UserService,
    private tokenService: TokenService,
    private router: Router) { }

  ngOnInit(): void {
    try {
      

      let idStr = localStorage.getItem("userId");
      let userId = idStr == null ? -1 : parseInt(idStr);
      if (userId != -1) 
        this.tokenService.getTokenByUserId();
      

      this.userService.findAll().subscribe(data => {
        this.users = data;
        }
      );
    } catch (error) {
      this.router.navigate(["/login"]);
    }
  }

}
