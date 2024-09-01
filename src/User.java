package src;

public class User {
   public static enum Role{
     ADMIN, USER
   }
   private String username;
   private Role role;
   private String password;
   
   public User(String username, Role role, String password) {
     this.username = username;
     this.role = role;
     this.password = password;
   }
   public String getUsername() {
     return username;
   }
   public Role getRole() {
     return role;
   }
   public String getPassword() {
     return password;
   }
   public boolean isPermittedToAddBook(){
     return role == Role.ADMIN;
   }
}