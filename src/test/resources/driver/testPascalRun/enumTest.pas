(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 02/01/2022
 *)
//{$mode tp}
program enumTest(output);
type
  cardsuit = (clubs, diamonds, hearts, spades);
//  intType = Integer;
//  stringType = string; {not allowed}
  //  cardsuit3 = (clubs3, clubs3); duplicate id is not allowed
const
  cardConst =diamonds;
var
  card : cardsuit;
  cardsuit2 : (clubs2, diamonds2, hearts2, spades2);
  boolVar:Boolean;
begin
  boolVar :=  true= (diamonds < diamonds);
  card:= Clubs;
  card:= cardConst;
  Write(ord(card));

  cardsuit2:= clubs2;
  Write(ord(cardsuit2));

  // relational operator
  boolVar := clubs2<diamonds2;
  boolVar := clubs>=diamonds;
  WriteLn(boolVar);
end.