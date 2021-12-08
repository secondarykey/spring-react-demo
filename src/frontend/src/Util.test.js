import Util from "./Util"
describe("Util", ()=> {
    test("zeroPadding()", () => {
        expect(Util.zeroPadding(10,3)).toBe("010");
        expect(Util.zeroPadding("20",3)).toBe("020");
        expect(Util.zeroPadding(20,2)).toBe("20");
        expect(Util.zeroPadding(100,4)).toBe("0100");
    })

    test("formatDate()", () => {
        let d = new Date("2021/12/31");
        expect(Util.formatDate(d)).toBe("2021-12-31");
        d = new Date(1988,1,28,23,22,22);
        expect(Util.formatDate(d)).toBe("1988-02-28");
        d = new Date(1511782200000);
        expect(Util.formatDate(d)).toBe("2017-11-27");
    })

    test("nowString()", () => {
        let got = Util.formatDate(new Date());
        expect(Util.nowString()).toBe(got);
    })

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