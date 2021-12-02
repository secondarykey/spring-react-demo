import React from "React";
import {render} from "@testing-library/react";
import {Authentication,Name} from "./Authentication";

describe("Authentication Render()",() => {
    test("render() Authentication component", () =>{
        const {container} = render(<Authentication />);
        console.log(container.Name());
    });
})
