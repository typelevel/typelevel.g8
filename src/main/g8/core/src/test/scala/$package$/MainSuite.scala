package $package$

import munit.CatsEffectSuite

class MainSuite extends CatsEffectSuite {

  test("Main should exit succesfully") {
    val main = Main.run.attempt
    assertIO(main, Right(()))
  }

}
