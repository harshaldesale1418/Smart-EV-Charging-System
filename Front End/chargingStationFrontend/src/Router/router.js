const routes = {
  '/': MainPage,
  '/index.html': MainPage,
  '/station':chargingStationDashboard,
  '/update/station':updateStationInfo,
  '/user':UserLandingPage,
  '/charpoints/all': showAllChargingStations
};

function navigateTo(route) {
  window.history.pushState({}, route, window.location.origin + route);
  if (routes[route]) {
      routes[route]();
  } else {
      console.error("Route not found:", route);
  }
}

function handleNavigation(e) {
  e.preventDefault();
  const currentRoute = window.location.pathname;

  const params = new URLSearchParams(window.location.search);
  const token = params.get('email');
  const role = params.get('role');
  if(token !==null){
    localStorage.setItem('station-cookie',token);
    localStorage.setItem('role',role);
    if(role==='chargingstation'){
      const chargepoint=getChargingStationById();
      console.log(chargepoint.name);
      if(chargepoint.name ==null || chargepoint.name===''){
        navigateTo('/update/station')
      }else{
        navigateTo('/station')   
      }
    }else if(role==='user'){
      navigateTo('/user');
    }else{
      navigateTo('/')
    }
  }else{
    navigateTo(currentRoute);
  }
}

window.addEventListener('popstate', handleNavigation);
window.addEventListener('load', handleNavigation);