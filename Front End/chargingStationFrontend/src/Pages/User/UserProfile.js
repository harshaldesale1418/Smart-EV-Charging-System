 function UserProfile(){
  document.body.innerHTML('');
  const container = document.createElement('div');
  container.classList.add('container');

  const userProfileHeading = document.createElement('h1');
  userProfileHeading.textContent = 'User Profile';
  container.appendChild(userProfileHeading);

  const profileInfoDiv = document.createElement('div');
  profileInfoDiv.classList.add('profile-info');

  const userNameHeading = document.createElement('h2');
  userNameHeading.textContent = "Harshal";
  profileInfoDiv.appendChild(userNameHeading);

  const userEmailParagraph = document.createElement('p');
  userEmailParagraph.textContent = 'Email: mohanroymuntala77@gmail.com';
  profileInfoDiv.appendChild(userEmailParagraph);

  container.appendChild(profileInfoDiv);

  const myBookingsBtn = document.createElement('a');
  myBookingsBtn.href = '#';
  myBookingsBtn.classList.add('edit-profile-btn');
  myBookingsBtn.id = 'my-bookings-btn';
  myBookingsBtn.textContent = 'My Bookings';
  container.appendChild(myBookingsBtn);

  const currentBookingsDiv = document.createElement('div');
  currentBookingsDiv.classList.add('bookings');
  currentBookingsDiv.id = 'my-bookings';
  document.body.appendChild(container);

};
document.addEventListener('DOMContentLoaded', function() {
  UserProfile();
});