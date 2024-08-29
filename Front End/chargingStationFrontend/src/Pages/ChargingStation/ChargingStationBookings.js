function showChargingStationBooking(bookingsContainerDiv){
    const slottableFeild=['Time','User','SlotId','Status']
    const slottabledata=[
      {
        id:1,
        time:'11',
        slotId:1,
        status:'confirmed',
        user:'vinod'
      }
    ]
  const table=document.createElement("table")
  bookingsContainerDiv.appendChild(table);

  const tableHeader=document.createElement('thead');
  const tr=document.createElement('tr');

  slottableFeild.map(item=>{
    const th=document.createElement('th');
    th.textContent=item;
    tr.appendChild(th);
  });
  tableHeader.appendChild(tr);
  table.appendChild(tableHeader);
  bookingsContainerDiv.appendChild(table);
}