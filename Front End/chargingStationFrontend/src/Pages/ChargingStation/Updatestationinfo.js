  
  function updateStationInfo() {
    document.body.innerHTML = '';
    const formContainer = document.createElement('div');
    formContainer.classList.add('form-container');
    document.body.appendChild(formContainer);
  
    const heading = document.createElement('h2');
    heading.textContent = 'Update Station Profile';
    formContainer.appendChild(heading);
  
    const form = document.createElement('form');
    form.setAttribute('action', '');
    form.setAttribute('id', 'profileForm');
    formContainer.appendChild(form);
  
    const columnLeft = document.createElement('div');
    columnLeft.classList.add('column-left');
    form.appendChild(columnLeft);
  
    const labels = ['Name:', 'Opening Time:', 'Closing Time:','Latitude','Longitude'];
    const inputIds = ['name',  'openTime', 'closeTime','latitude','longitude'];
    const inputTypes = ['text', 'number', 'number','text','text'];
  
    labels.forEach((labelText, index) => {
        const label = document.createElement('label');
        label.setAttribute('for', inputIds[index]);
        label.textContent = labelText;
  
        const input = document.createElement('input');
        input.setAttribute('type', inputTypes[index]);
        input.setAttribute('name', inputIds[index]);
        input.setAttribute('id', inputIds[index]);
        input.setAttribute('required', '');
  
        columnLeft.appendChild(label);
        columnLeft.appendChild(input);
    });
    const getlocation=document.createElement('button');
    getlocation.setAttribute('type', 'button');
    getlocation.textContent="add auto location";  
    getlocation.onclick=function(){
      const loc=getCurrentPositionUser();
      const latitudeInput = document.getElementById('latitude');
      const longitudeInput = document.getElementById('longitude');
      latitudeInput.value = loc.lat;
      longitudeInput.value = loc.long;
    }
    columnLeft.appendChild(getlocation);
    const submitButton = document.createElement('button');
    submitButton.setAttribute('type', 'button');
    submitButton.textContent = 'Submit';
    submitButton.onclick=function(){
      submitForm();
    }
    columnLeft.appendChild(submitButton);
  }

  function submitForm() {
    const formData = new FormData(document.getElementById('profileForm'));

    const formDataObject = {};

    for (let [key, value] of formData.entries()) {
      if(key==='openTime'|| key==='closeTime'){
        value=parseInt(value);
      }
      if(key==='longitude'|| key==='latitude'){
        value=parseFloat(value);
      }
      formDataObject[key] = value;
    }
    console.log(formDataObject);
    getChargingStationById().then(data=>{
      console.log(data);
      formDataObject['emailId']=data.emailId;
      updateChargingStationProfile(formDataObject)
    })
  }