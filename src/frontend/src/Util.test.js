import Util from "./Util"
describe("Util", ()=> {

    test("matchStringGlob()", () => {
        expect(Util.matchStringGlob("/","/")).toBe(true);
        expect(Util.matchStringGlob("/","/test")).toBe(false);
        expect(Util.matchStringGlob("/*","test")).toBe(false);

        expect(Util.matchStringGlob("/*","/test")).toBe(true);
        expect(Util.matchStringGlob("*/*","test")).toBe(false);
        expect(Util.matchStringGlob("*/*","te/st")).toBe(true);
    })

    test("match()", () => {
        expect(Util.match("/",undefined)).toBe(undefined);
        expect(Util.match("/",null)).toBe(undefined);
        expect(Util.match("/","")).toBe(undefined);

        expect(Util.match("/",["/","/message/*"])).toBe(true);
        expect(Util.match("/pages/menu",["/","/message/*"])).toBe(false);
        expect(Util.match("/message/id",["/","/message/*"])).toBe(true);
    })
});