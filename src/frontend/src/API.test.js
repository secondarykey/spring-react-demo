import Layout from "./Layout";
import API, {isUnknownError} from "./API";
import { render } from "@testing-library/react";
import { act } from "react-dom/test-utils";

describe("API",() => {
    test("API isUnknownError",() => {
        act( () => {
          const {container} = render(<Layout><div/></Layout>);
          var val = API.isUnknownError();
          expect(val).toBe(true);
        })
    })
}) 