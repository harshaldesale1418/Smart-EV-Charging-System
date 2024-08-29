const getCurrentPositionUser=()=>{
    let arr={
        lat:20.5937,
        long:78.9629
    }
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            arr.lat = position.coords.latitude;
            arr.long = position.coords.longitude;
            return arr;
        });
    } 
    return arr;
};

const RenderMap=()=>{
    const map = L.map("map");
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    }).addTo(map);
    const MyLocationIcon=L.divIcon({
        html:'<svg width="30px" height="30px" viewBox="0 0 " fill="none" xmlns="http://www.w3.org/2000/svg" stroke="#00bfff" transform="rotate(0)"><g id="SVGRepo_bgCarrier" stroke-width="0" transform="translate(4.199999999999999,4.199999999999999), scale(0.65)"><rect x="0" y="0" width="24.00" height="24.00" rx="12" fill="#7ed0ec" strokewidth="0"></rect></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#c9c5c5" stroke-width="0.192"></g><g id="SVGRepo_iconCarrier"> <path d="M3 12H21M12 3V21M19 12C19 15.866 15.866 19 12 19C8.13401 19 5 15.866 5 12C5 8.13401 8.13401 5 12 5C15.866 5 19 8.13401 19 12Z" stroke="#007bff" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>',
        iconSize: [25, 25],
        iconAnchor: [15, 30] 
    })
    getAllChargingStation().then(data=>{
        console.log(data);
        data.map(item=>{

            L.marker([item.latitude,item.longitude]).addTo(map);
        })
        
    })
    const userlocation = getCurrentPositionUser();
    L.marker([userlocation.lat,userlocation.long]).addTo(map);
    map.setView([userlocation.lat,userlocation.long],13);
};
const RederChargingStations=(chargingStation)=>{
    const elements = document.querySelector(`.stationcards`);
    console.log(chargingStation);
    chargingStation.map((item,key)=>{
        const card = document.createElement('div');
        card.classList.add('stationcard');
        card.addEventListener('click',()=>changePage("booking_page"));
        
        const chargeStationname=document.createElement('p')
        chargeStationname.textContent=item.name;

        card.appendChild(chargeStationname);
        elements. appendChild(card);
    })
};

const decodeJwtToken=(token) =>{
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
  
    return JSON.parse(jsonPayload);
  }