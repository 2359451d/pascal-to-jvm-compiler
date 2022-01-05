(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program subrangeTest;
type
  weather = (sunny,rainy);
  DaysOfWeek = (Sunday, Monday, Tuesday, Wednesday,
                 Thursday, Friday, Saturday);
  DaysOfWorkWeek = Monday..Friday;

  TNumber2 = 10 .. 123;
  TLetter2 = 'A' .. 'Z';

const
  weatherConst = sunny;
  intConst1=1;
  intConst2=100;
var
  weatherVar: weather;

  marks: 1 .. 100;
  largeMark:1..101;
  marksVar: intConst1..intConst2;

  grade: 'A' .. 'E';
  weatherSubrange: sunny..rainy;
  flag:  false..true;
  flag2: false..false;

  sub : haha..hehe; {enum id not define}
  invalidFlag: true..false; {invalid bound}
  invalidStringSubrange: 'Z'..'A'; {invalid bound}
  invalidIntSubrange: 123..10; {invalid bound}
  invalidweatherSubrange1: rainy..sunny; {invalid bound}
  invalidweatherSubrange2: sunny..Monday; {invalid bound, differnet kind}

  tbNumber1 : 10 .. 123;
  tbLetter1 : 'A' .. 'Z';

  tbNumber2 : TNumber2 ;
  tbLetter2 : TLetter2 ;

  flagVar: Boolean;
  int1: Integer;
begin
  int1 := 1000;
  flag := false;
  flag2 := true; {range not valid}

  weatherVar := weatherConst;
  weatherVar := sunny;
  weatherVar := cloudy;{Not defined}
  weatherVar := Monday; {enum type is not of the same kind}

  weatherSubrange :=sunny;
  weatherSubrange :=Sunny;

  weatherSubrange :=cloudy;{not defined}
  weatherSubrange :=Monday; {value not in the valid range}

  tbNumber1 := 10;
  tbNumber1 := 123;
  tbNumber2 := 10;
  tbNumber2 := 123;
  tbLetter1 := 'F';
  tbLetter2 := 'F';

  { the values are outside the subrange}
  tbNumber1 := 9;
  tbNumber2 := 124;
  tbLetter1 := 'f';
  tbLetter2 := 'f';
  { the values are string literals}
  tbNumber1 := '10';
  tbNumber1 := '123';
  tbNumber2 := '10';
  tbNumber2 := '123';



  flag:= marks=marksVar;
  flag:=false;
  flag:= true<flag;

  marksVar:=100;
  largeMark := intConst2+1;
  largeMark := intConst2 + 2; {Compile time check, as intConst2 is constant}

  marks:=100;
  largeMark:= marks+1; {RUNTIME check, should not raise here}
  largeMark:= marks + marks+1; {RUNTIME check, should not raise here}

end.