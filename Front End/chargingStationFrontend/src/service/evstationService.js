const getAllChargingStation=async()=>{
    const res =await fetch(`http://localhost:8081/chargingstation/all`,{
        method:"GET",
        headers:{
            'Content-Type':"application/json"
        }
    })
    return res.json()
};
const loginchargingStation =async()=>{
    window.location.href='http://localhost:8081/auth/chargingstation';
};
const loginuser =async()=>{
    window.location.href='http://localhost:8081/auth/user';
};
const LoginUser =async()=>{
    const res=await fetch('http://localhost:8081/auth/getuser',{
        method:"GET",
        headers:{
            'Content-Type':"application/json"
        },
    
    })
    return res;
};
const getChargingStationById =async()=>{
    const token =localStorage.getItem('station-cookie')
    if(token){

        const decodedtoken=decodeJwtToken(token);
        const stationId=decodedtoken.sub;
        const res=await fetch(`http://localhost:8081/chargingstation/${stationId}`,{
            method:"GET",
            headers:{
                'Content-Type':"application/json",
                'Authorization':token
            },
        
        })
        return res.json();
    }else{
        navigateTo('/')
    }
};
const updateChargingStationProfile =async(data)=>{
    const token =localStorage.getItem('station-cookie')
    if(token){

        const decodedtoken=decodeJwtToken(token);
        const stationId=decodedtoken.sub;
        const res=await fetch(`http://localhost:8081/chargingstation/update`,{
            method:"PUT",
            headers:{
                'Content-Type':"application/json",
                'Authorization':token,
            },
            body:JSON.stringify(data)
        
        })
        if(res.status===201){

            console.log(res.json());
            navigateTo('/station')

        }
    }else{
        navigateTo('/')
    }
};
const getAllChargingStationSlots =async()=>{
    const token =localStorage.getItem('station-cookie')
    if(token){

        const decodedtoken=decodeJwtToken(token);
        const stationId=decodedtoken.sub;
        const res=await fetch(`http://localhost:8081/chargingslot/all/${stationId}`,{
            method:"GET",
            headers:{
                'Content-Type':"application/json",
                'Authorization':token
            },
        })
        
        if(res.ok){

            return res.json();
        }
    }else{
        navigateTo('/')
    }
};
const addChargingSlotStation =async(data)=>{
    const token =localStorage.getItem('station-cookie')
    if(token){
        const decodedtoken=decodeJwtToken(token);
        const stationId=decodedtoken.sub;
        const res=await fetch(`http://localhost:8081/chargingslot/addslot/${stationId}`,{
            method:"POST",
            headers:{
                'Content-Type':"application/json",
                'Authorization':token
            },
            body:JSON.stringify(data)
        })
        if(res.status===201){
            navigateTo('/station')
            console.log( res.json());
            return res.json();
        }
    }else{
        navigateTo('/')
    }
};
const getAllUserBooking = async() =>{
    const token = localStorage.getItem('web-vb-token');
    if(token){
        const decodedtoken=decodeJwtToken(token);
        const userId=decodedtoken.sub;
        const res=await fetch(`http://localhost:8081/booking/user/${userId}`,{
            method:"GET",
            headers:{
                'Content-Type':"application/json",
                'Authorization':token,
            },
        })
        
        if(res.ok){

            return res.json();
        }
    }else{
        navigateTo('/')
    }
};