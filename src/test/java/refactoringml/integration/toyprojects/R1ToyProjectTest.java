package refactoringml.integration.toyprojects;

import refactoringml.integration.IntegrationBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.quarkus.test.junit.QuarkusTest;
import refactoringml.db.RefactoringCommit;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
public class R1ToyProjectTest extends IntegrationBaseTest {

	@Override
	protected String getRepo() {
		return "https://github.com/refactoring-ai-testing/toyrepo-r1.git";
	}

	@Test
	public void refactorings() {
		Assertions.assertEquals(3, getRefactoringCommits().size());

		assertRefactoring(getRefactoringCommits(), "e8895b22847c7c54a9e187f9f674db274e6bc103", "Extract Method", 1);

		for (RefactoringCommit refactoringCommit : getRefactoringCommits()){
			Assertions.assertFalse(refactoringCommit.classMetrics.isInnerClass);
		}
	}

	@Test
	public void stable() {
		// there are no instances of stable variables, as the repo is too small
		Assertions.assertEquals(0, getStableCommits().size());
	}

	@Test
	public void commitMetaData(){
		String commit = "21151bf7e36da52b9305d99755eb6f0b7616e620";
		assertMetaDataRefactoring(
				commit,
				"inline method",
				"Inline Method\tprivate convert(a int) : int inlined to public m1() : void in class a.Example1",
				"@local//tmp/toyrepo-r1/" + commit,
				6,
				"a6d21e18c680431b0d4a09374e31a72144a728dc");
	}

	@Test
	public void projectMetrics() {
		assertProjectMetrics(1, 1, 0, 9, 9, 0);
	}
}