let chargepointId = 1;

function showAllChargingStations() {
    document.body.innerHTML = '';
    renderNavbar();
    const cardContainer = document.createElement("div");
    cardContainer.classList.add('cardContainer'); 
    getAllChargingStation().then(data => {
        console.log(data);
        data.map(item => {
            const card = document.createElement('div');
            card.classList.add('card');
            card.onclick = () => {
                UserBooking(item.stationId);
            };

            const name = document.createElement('h3');
            name.textContent = item.name;
            card.appendChild(name);

            cardContainer.appendChild(card);

        });
    });
    document.body.appendChild(cardContainer);
}
