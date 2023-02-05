import axios from "axios";

export default () => {
    return axios.create( {
        baseURL: "https://api.unsplash.com/", headers: {
            Authorization: "Client-ID 278xxxxxxxxxxxxxxxxxxxxxxxxxxxxx2d1"
        }
    } );
}