import React from "react"

import Package from "../../../package.json";

const imageURL = "images/"
function getEndpoint() {
    let home = Package.homepage;
    let endpoint = Package.proxy + home;
    return endpoint;
}

function getImageURL(id) {
    var endpoint = getEndpoint();
    var url = endpoint + imageURL + id;
    return url;
}

class Images extends React.Component {
    constructor(props) {
        super(props);
        this.src = getImageURL(props.src);
    }
    render() {
        return (<>
          <img src={this.src} alt={this.props.src} className={this.props.className} />
        </>)
    }
}

export function GetImageURL(id) {
    return getImageURL(id);
}

export default Images;