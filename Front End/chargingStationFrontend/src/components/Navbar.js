function renderNavbar() {
    const navbar = document.createElement("div");
    navbar.className = "navbar";
    const logoLink = document.createElement("div");
    logoLink.className = "logo";
    const logoImg = document.createElement("img");
    logoImg.src = "/logo.jpg";
    logoImg.alt = "Logo";
    logoLink.appendChild(logoImg);
    
    const logoTitle = document.createTextNode("EV Charging Station");
    logoLink.appendChild(logoTitle);
    
    navbar.appendChild(logoLink);
    
    const selectStation = document.createElement("p");
    selectStation.className ="selectStation";
    selectStation.textContent =  "Chargepoints";
    selectStation.onclick = () =>{
        navigateTo('/charpoints/all');
    }
    navbar.appendChild(selectStation);
    const loginLink = document.createElement("div");
    loginLink.classList.add='logindiv';
    const isToken=localStorage.getItem('web-vb-token');
    if(isToken){
        const userimage = document.createElement("img");
        userimage.src = "R.png";
        userimage.alt = "Logo";
        loginLink.appendChild(userimage)
    }else{
        loginLink.className = "login";
        loginLink.textContent = "User Login";
    }
    loginLink.onclick=()=>{
        loginuser();
    }
    navbar.appendChild(loginLink);
    document.body.appendChild(navbar);
}