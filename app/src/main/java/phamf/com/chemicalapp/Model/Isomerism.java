package phamf.com.chemicalapp.Model;

public class Isomerism {

    int id;

    String molecule_formula;

    String normal_name;

//    String type;

    String replace_name;

    String structure_image_id = "";

    String compact_structure_image_id = "";

    public Isomerism() {

    }

    public Isomerism(/*String type,*/ String molecule_formula, String normal_name, String replace_name, String structure_image_id, String compact_structure_image_id) {
        this.molecule_formula = molecule_formula;
        this.normal_name = normal_name;
        this.replace_name = replace_name;
        this.structure_image_id = structure_image_id;
        this.compact_structure_image_id = compact_structure_image_id;
//        this.type = type;
    }

//    public void setType(@IsomerismType.Type String type) {
//        this.type = type;
//    }
//
//    public String getType() {
//        return this.type;
//    }


    public int getId() {
        return id;
    }

    public void setId( int id) {
        this.id = id;
    }

    public String getMolecule_formula() {
        return molecule_formula;
    }

    public void setMolecule_formula(String molecule_formula) {
        this.molecule_formula = molecule_formula;
    }

    public String getNormal_name() {
        return normal_name;
    }

    public void setNormal_name(String normal_name) {
        this.normal_name = normal_name;
    }

    public String getReplace_name() {
        return replace_name;
    }

    public void setReplace_name(String replace_name) {
        this.replace_name = replace_name;
    }

    public String getStructure_image_id() {
        return structure_image_id;
    }

    public void setStructure_image_id(String structure_image_id) {
        this.structure_image_id = structure_image_id;
    }

    public String getCompact_structure_image_id() {
        return compact_structure_image_id;
    }

    public void setCompact_structure_image_id(String compact_structure_image_id) {
        this.compact_structure_image_id = compact_structure_image_id;
    }


}



