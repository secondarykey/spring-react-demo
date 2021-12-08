import React from "React";
import {render} from "@testing-library/react";
import Authentication from "./Authentication";
import {Name,LoginPage,Role} from "./Authentication";

describe("Authentication Component",() => {
    test("render()", () =>{
        const container = render(<Authentication/>);
        expect(container).toMatchSnapshot();
    });
})

describe("Authentication Export Functions",() => {
    test("Name()", () =>{
        const container = render(<Name />);
        expect(container).toMatchSnapshot();
    });
    test("LoginPage()", () =>{
        const container = render(<LoginPage>LoginChildren</LoginPage>);
        expect(container).toMatchSnapshot();
    });
    test("Role()", () =>{
        const container = render(<Role>RoleTest</Role>);
        expect(container).toMatchSnapshot();
    });
    test("CreateJWT()", () =>{
    });
    test("Logout()", () =>{
    });
});
