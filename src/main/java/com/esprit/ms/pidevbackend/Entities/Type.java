package com.esprit.ms.pidevbackend.Entities;



public enum Type {


    Protocoles_de_safety("Safety protocols"),
    Defective_equipment("Defective equipment"),
    Individual_protection("Individual protection"),
    Regulatory_non_compliance("Regulatory non-compliance"),
    Others("Others"),
    Apparent_disorders("Apparent disorders"),
    Structural_defects("Structural defects"),
    Hidden_defects("Hidden defects"),
    Insulation_and_ventilation("Insulation and ventilation"),
    Paints_and_coatings("Paints and coatings"),
    Installation_and_flashing_placement("Installation and flashing placement"),
    Deficient_plumbing_installation("Deficient plumbing installation"),
    Fire_partition("Fire partition"),
    Installation_and_placement_of_exterior_cladding("Installation and placement of exterior cladding"),
    Continuity_and_integrity_of_firewall("Continuity and integrity of firewall");

    private final String value;

    Type(String value) {
        this.value = value;
    }




}