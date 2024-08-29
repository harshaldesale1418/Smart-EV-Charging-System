const UserBooking=(chargepointId)=>{
  document.body.innerHTML='';
  renderNavbar();
  const chargingslotsContainer=document.createElement('div');
  chargingslotsContainer.classList.add("chargeslotscontainer")
  const bookingdata={};

  getAllChargingStationSlotsById(chargepointId).then(data=>{
    data.map((item)=>{
      const slotcard=document.createElement('div');
      slotcard.classList.add('charginslotcard')
      const p=document.createElement("p");
      p.textContent=`slotId ${item.slotId}`;

      slotcard.onclick=()=>{
        bookingdata['chargingSlotId']=data.slotId;
        renderDatepicker()
      }
      slotcard.appendChild(p);
      chargingslotsContainer.appendChild(slotcard);
    })
  });
  document.body.appendChild(chargingslotsContainer)
}

function renderDatepicker(){
   const container = document.createElement('div');
    container.classList.add('container');
    container.id = "cardContainer";
  
  const datepickerSection = document.createElement('div');
  datepickerSection.classList.add('datepicker');
  
  const datepickerTitle = document.createElement('h2');
  datepickerTitle.textContent = "Select Date:";
  
  const datePickerInput = document.createElement('input');
  datePickerInput.type = "date";
  datePickerInput.id = "date";
  datePickerInput.name = "date";
  datePickerInput.min = new Date().toISOString().split('T')[0];
  
  const submitButton = document.createElement('button');
  submitButton.textContent = "Submit";
  submitButton.onclick=()=>{
    renderTimeSlots(10,23);
  }
  
  datepickerSection.appendChild(datepickerTitle);
  datepickerSection.appendChild(datePickerInput);
  datepickerSection.appendChild(submitButton);
  

  const timeslotContainer = document.createElement('div');
  timeslotContainer.id = "timeslot";
  timeslotContainer.classList.add('timeslot');
  
  document.body.appendChild(container);
  document.body.appendChild(datepickerSection);
  document.body.appendChild(timeslotContainer);
  
}
function renderTimeSlots() {
  const datepicker = document.getElementById("date");
  const selectedDate = new Date(datepicker.value);
  const currentDate = new Date();
  const hourCardsContainer = document.getElementById('timeslot');
  hourCardsContainer.innerHTML = ''; 

  let currentHour = stationOpen;
  let currentMinute = 0;

  if (selectedDate.toDateString() === currentDate.toDateString()) {
    
      currentHour = Math.max(currentHour, currentDate.getHours() + 1);
     while ((currentHour < stationClose)&&(currentHour>=stationOpen)) {
      const card = document.createElement('div');
      card.classList.add('card');
      card.textContent = `${currentHour}:00 ` + "To " + `${currentHour + 1}:00`;
      hourCardsContainer.appendChild(card);
      
      currentHour++;
     }
  }
  else if (selectedDate > currentDate){
    for(let hour = stationOpen; hour<stationClose;hour++){
      const card = document.createElement('div');
      card.classList.add('card');
      card.textContent = `${hour}:00 ` + "To " + `${hour + 1}:00`;
      hourCardsContainer.appendChild(card);

    }
  }
  else{
    alert("please Don't Select Old Date")
  }
}

function bookSlot() {
  alert('Booking slot functionality will be implemented here.');
}