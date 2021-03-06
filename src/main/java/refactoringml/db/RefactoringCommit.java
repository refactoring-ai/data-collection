package refactoringml.db;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Table(name = "RefactoringCommit", indexes = {@Index(columnList = "project_id"), @Index(columnList = "level"), @Index(columnList = "refactoring"), @Index(columnList = "isTest"), @Index(columnList = "isValid")})
public class RefactoringCommit extends Instance {
	//Describe the refactoring e.g. "Rename Class" or "Extract Method"
	public String refactoring;
	//Describe the content of the refactoring e.g. "Rename Pets to Cat"
	@Lob
	public String refactoringSummary;

	//Is this refactoring instance valid, or do we have objections, e.g. to many (> 50) refactorings on the same class file and commit?
	//TODO: currently, this evaluated by the database, instead do it in the data-collection pipeline
	public Boolean isValid = true;
	

	public RefactoringCommit() {}

	public RefactoringCommit(Project project, CommitMetaData commitMetaData, String filePath, String className,
			   String refactoring, int refactoringLevel, String refactoringSummary,
			   ClassMetric classMetrics, MethodMetric methodMetrics, VariableMetric variableMetrics, FieldMetric fieldMetrics) {
		super(project, commitMetaData, filePath, className, classMetrics, methodMetrics, variableMetrics, fieldMetrics, refactoringLevel);
		this.refactoring = refactoring;
		this.refactoringSummary = refactoringSummary.trim();
	}

	@Override
	public String toString() {
		return "RefactoringCommit{" +
				"id=" + 
				super.toString() +
				", isValid=" + isValid + '\'' +
				", refactoring='" + refactoring + '\'' +
				", refactoringSummary=" + refactoringSummary +
				'}';
	}

	@Transactional
    public static long countForProject(Project project) {
        return count("project", project);
    }

}