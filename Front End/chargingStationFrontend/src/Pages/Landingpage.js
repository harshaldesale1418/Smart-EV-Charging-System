const MainPage=()=> {
    document.body.innerHTML = '';
    const header = document.createElement("header");
    header.classList.add='header'
    const h1 = document.createElement("h1");
    h1.textContent = "EV Charging Station";
    header.appendChild(h1);
    document.body.appendChild(header);
  
    const containerDiv = document.createElement("div");
    containerDiv.className = "landingpage";
 
    const descriptionDiv = document.createElement("div");
    descriptionDiv.className = "description";
  
    const descriptionParagraph = document.createElement("p");
    descriptionParagraph.textContent = "Welcome to our EV charging station! We provide fast and reliable charging services for electric vehicles. Whether you're a user looking to charge your car or a station owner interested in our services, we've got you covered.";
 
    descriptionDiv.appendChild(descriptionParagraph);

    const buttonContainerDiv = document.createElement("div");
    buttonContainerDiv.className = "button-container";

    const userButton = document.createElement("a");
    userButton.onclick = () =>{
        localStorage.clear();
        navigateTo('/user');
    }
    userButton.className = "user-button";
    userButton.textContent = "For Users";

    const stationOwnerButton = document.createElement("a");
    stationOwnerButton.onclick = () =>{
      loginchargingStation();
  
    }
    stationOwnerButton.className = "station-button";
    stationOwnerButton.textContent = "For Station Owners";

    buttonContainerDiv.appendChild(userButton);
    buttonContainerDiv.appendChild(stationOwnerButton);

    containerDiv.appendChild(descriptionDiv);
    containerDiv.appendChild(buttonContainerDiv);
    document.body.appendChild(containerDiv);
  }
  