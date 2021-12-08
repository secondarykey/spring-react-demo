import Encrypt from "./Encrypt"

describe("Encrypt Test", () => {

    test("Object1", () => {
      var obj = {}
      obj.name = "name"
      obj.id = "aaaa"
      var data = Encrypt.encode(obj);
      var decObj = Encrypt.decode(data);
      expect(decObj.id).toBe(obj.id);
      expect(decObj.name).toBe(obj.name);
    })

    test("Object2", () => {
      var obj = {}
      obj.num = 19;
      obj.expiry = "2020/10/20 20:20:30";

      var data = Encrypt.encode(obj);
      console.log(data);
      var decObj = Encrypt.decode(data);

      expect(decObj.num).toBe(obj.num);
      expect(decObj.expiry).toBe(obj.expiry);
    });

})