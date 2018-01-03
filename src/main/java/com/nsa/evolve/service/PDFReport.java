package com.nsa.evolve.service;

import com.nsa.evolve.form.PDFForm;

/**
 * Created by c1633899 on 10/12/2017.
 */
public interface PDFReport {

    byte[] createReport(PDFForm form);
}
