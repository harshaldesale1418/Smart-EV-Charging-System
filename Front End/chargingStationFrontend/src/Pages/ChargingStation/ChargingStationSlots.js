function showChargingStationSlots(slotsContainerDiv){
    const slottableFeild=['Id','Name','Status','price']
    const table=document.createElement("table")

    const tableHeader=document.createElement('thead');
    const tr=document.createElement('tr');

    slottableFeild.map(item=>{
      const th=document.createElement('th');
      th.textContent=item;
      tr.appendChild(th);
    });
    tableHeader.appendChild(tr);
    table.appendChild(tableHeader);
  getAllChargingStationSlots().then(data=>{
  data.map(item=>{
  const tabledata=document.createElement('tbody')
    const tr=document.createElement('tr');

    const id=document.createElement('td');
    id.textContent=item.slotId;
    tr.appendChild(id);
    tabledata.appendChild(tr);

    const name=document.createElement('td');
    name.textContent=`slot ${item.slotId}`;
    tr.appendChild(name);
    const status=document.createElement('td');
    status.textContent=item.available;
    tr.appendChild(status);
    const pricePerHour=document.createElement('td');
    pricePerHour.textContent=item.pricePerHour;
    tr.appendChild(pricePerHour);
    
    tabledata.appendChild(tr);
    table.appendChild(tabledata);
  })

});

    slotsContainerDiv.appendChild(table);
}

function addSlot(slotOption) {
  slotOption.innerHTML = '';
  const container = document.createElement('div');
  container.classList.add('addslotcontainer');

  const inp = document.createElement('input');
  inp.setAttribute('type', 'text'); 
  inp.setAttribute('id', 'priceInput'); 
  inp.setAttribute('placeholder', 'please enter price in INR'); 
  container.appendChild(inp);

  const addbtn = document.createElement('button');
  addbtn.classList.add('stationoptionbtn');
  addbtn.textContent = 'Add';

  addbtn.onclick=()=>{
    const priceInputValue = inp.value;
    const data={};
    data['pricePerHour']=priceInputValue;
    data['available']=true;
    console.log(data);
    addChargingSlotStation(data);
  }
  
  container.appendChild(addbtn);
  slotOption.appendChild(container);
}