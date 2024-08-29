function UserLandingPage() {
    document.body.innerHTML = '';
    renderNavbar();
    const searchBar = document.createElement("div");
    searchBar.className = "searchBar";

    const searchContainer = document.createElement("div");
    searchContainer.className = "search-container";

    const searchInput = document.createElement("input");
    searchInput.type = "text";
    searchInput.id = "searchInput";
    searchInput.placeholder = "Search for a location";

    const searchButton = document.createElement("button");
    searchButton.id = "searchButton";
    searchButton.textContent = "Search";

    searchContainer.appendChild(searchInput);
    searchContainer.appendChild(searchButton);

    searchBar.appendChild(searchContainer);
    
   document.body.appendChild(searchBar);

    const mainPage = document.createElement("div");
    mainPage.className = "mainpage";

    const mapContainer = document.createElement("div");
    mapContainer.id = "map";
    mainPage.appendChild(mapContainer);
    document.body.appendChild(mainPage);
    RenderMap();
    
    const footer = document.createElement("footer");
    mainPage.appendChild(footer);
  }