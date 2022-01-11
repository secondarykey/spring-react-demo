import { render,screen } from "@testing-library/react";
import { act } from "react-dom/test-utils";

import Test from "../../TestPages"
import View from "./View"

describe("Todo/View",() => {
    test("Todo View",() => {
        act( () => {
            const container = render(<Test><View /></Test>)
            screen.debug();
            expect(container).toMatchSnapshot();
        })

    })
})