import React from "react";

class Select extends React.Component  {

    handleChange = (e) => {
        var idx = e.target.selectedIndex;
        var val = e.target.options[idx];
        this.props.onChange(val.value);
    }

    render() {

        let vals = this.props.values;
        if ( vals === undefined || vals === null ) {
            vals = [];
        }

        let clazz = "form-select";
        if ( this.props.className !== undefined ) {
            clazz += " " + this.props.className;
        }

        if ( !Array.isArray(vals) ) {
            return (<>
            SelectTag values is not array
            </>)
        }

        return (<>
          <select 
            className={clazz} 
            defaultValue={this.props.value} 
            onChange={this.handleChange}>
              { this.props.empty &&
                <option value=""></option>
              }
              { vals.map( (text,key)  => {
                return <option value={key} key={key}>{text}</option>
              })}
          </select>
        </>)
    }
}

export default Select;