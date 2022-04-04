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

  tbNumber1 : 10 .. 123;
  tbLetter1 : 'A' .. 'Z';

  tbNumber2 : TNumber2 ;
  tbLetter2 : TLetter2 ;

  flagVar: Boolean;
  int1: Integer;
begin
  int1 := 1000;
  flag := false;

  weatherVar := weatherConst;
  weatherVar := sunny;

  weatherSubrange :=sunny;
  weatherSubrange :=Sunny;


  tbNumber1 := 10;
  tbNumber1 := 123;
  tbNumber2 := 10;
  tbNumber2 := 123;
  tbLetter1 := 'F';
  tbLetter2 := 'F';

  { the values are outside the subrange}
  tbNumber1 := 10;
  tbNumber2 := 123;
  tbLetter1 := 'A';
  tbLetter2 := 'A';

  flag:= marks=marksVar; {runtime check}
  flag:=false;
  flag:= true<flag; {runtime check}

  marksVar:=100;
  largeMark := intConst2+1;
  largeMark := intConst2 + 2; {Compile time check, as intConst2 is constant}

  marks:=100;
  largeMark:= marks+1; {RUNTIME check, should not raise here}
  largeMark:= marks + marks+1; {RUNTIME check, should not raise here}

end.
