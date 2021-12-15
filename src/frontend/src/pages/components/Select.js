import React from "react";

class Select extends React.Component  {


    handleChange = (e) => {
        var idx = e.target.selectedIndex;
        var val = e.target.options[idx];
        if ( this.props.onChange !== undefined ) {
          this.props.onChange(val.value);
        }
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

        var optionList = []
        if ( Array.isArray(vals) ) {
          vals.map( (text,key)  => {
            optionList.push(<option value={key} key={key}>{text}</option>)
            return true;
          })
        } else if ( (typeof vals) == "object" )  {
          for ( let key in vals ) {
            optionList.push(<option value={key} key={key}>{vals[key]}</option>)
          }
        } else {
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
              {optionList}
          </select>
        </>)
    }
}

export default Select;