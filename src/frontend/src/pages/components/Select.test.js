import React from "react";
import {render,screen} from "@testing-library/react";
import Select from "./Select";

describe("Select Component",() => {
    test("Select default Render()", () => {
        const select = render(<Select/>);
        expect(select).toMatchSnapshot();
    })

    test("Select values null Render()", () => {
        const select = render(<Select values={null} />);
        expect(select).toMatchSnapshot();
    })
})