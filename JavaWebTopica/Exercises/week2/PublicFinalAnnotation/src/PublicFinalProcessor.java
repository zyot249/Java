import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes(value = {"PublicFinal"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PublicFinalProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment env) {
        filer = env.getFiler();
        messager = env.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        DevLog.log("\n\n");
        DevLog.log(" ======================================================== ");
        DevLog.log("#process(...) in " + this.getClass().getSimpleName());
        DevLog.log(" ======================================================== ");

        // annotations ở đây mô tả các annotation
        // thuộc phạm vi sử lý của Processor này.
        // Vì Processor này được định nghĩa chỉ dùng cho @PublicFinal
        // cho nên chắc chắn annotations chỉ có 1 phần tử.
        DevLog.log(" annotations count = " + annotations.size());

        // TypeElement mô tả các annotation
        // thuộc phạm vi Processor này sử lý.
        for (TypeElement ann : annotations) {

            // Các phần tử được chú thích bởi Annotation @PublicFinal
            // Element ở đây mô tả một đối tượng được @PublicFinal chú thích
            Set<? extends Element> e2s = env.getElementsAnnotatedWith(ann);
            for (Element e2 : e2s) {
                DevLog.log("- e2 = " + e2);

                Set<Modifier> modifiers = e2.getModifiers();

                // @PublicFinal chỉ áp dụng cho public & final
                // Thông báo nếu sử dụng sai.
                if (!(modifiers.contains(Modifier.FINAL) && modifiers
                        .contains(Modifier.PUBLIC))) {
                    DevLog.log("- Error!!!");
                    messager.printMessage(Diagnostic.Kind.ERROR,
                            "Method/field wasn't public and final", e2);

                }
            }
        }

        // Tất cả đã được sử lý bởi Processor này.
        return true;

    }
}
