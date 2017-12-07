const api_url=function(){
    // let api_url="http://localhost:8080"; //TODO Zmien na poprawny URL
    let api_url ="http://37.233.102.142:8080/";
    // if(process.env.NODE_ENV == 'local'){
    //     //local api url
    // }
    // else if(process.env.NODE_ENV == 'development'){
    //     //dev api url
    // }
    return api_url;
};

export const url= api_url();