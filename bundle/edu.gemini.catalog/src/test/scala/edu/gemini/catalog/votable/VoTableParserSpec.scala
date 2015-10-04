package edu.gemini.catalog.votable

import java.net.URL

import edu.gemini.catalog.api.{SIMBAD, PPMXL, UCAC4}
import edu.gemini.spModel.core._
import edu.gemini.spModel.core.Target.SiderealTarget
import org.specs2.mutable.SpecificationWithJUnit
import squants.motion.KilometersPerSecond

import scalaz._
import Scalaz._

class VoTableParserSpec extends SpecificationWithJUnit with VoTableParser {

  "Ucd" should {
    "detect if is a superset" in {
      Ucd("stat.error;phot.mag;em.opt.i").includes(UcdWord("phot.mag")) should beTrue
      Ucd("stat.error;phot.mag;em.opt.i").matches("phot.mag".r) should beTrue
      Ucd("stat.error;phot.mag;em.opt.i").matches("em.opt.(\\w)".r) should beTrue
    }
  }

  "The VoTable Parser" should {
    val fieldsNode =
      <TABLE>
        <FIELD ID="gmag_err" datatype="double" name="gmag_err" ucd="stat.error;phot.mag;em.opt.g"/>
        <FIELD ID="rmag_err" datatype="double" name="rmag_err" ucd="stat.error;phot.mag;em.opt.r"/>
        <FIELD ID="flags1" datatype="int" name="flags1" ucd="meta.code"/>
        <FIELD ID="ppmxl" datatype="int" name="ppmxl" ucd="meta.id;meta.main"/>
      </TABLE>

    val tableRow =
      <TR>
        <TD>0.0960165</TD>
        <TD>0.0503736</TD>
        <TD>268435728</TD>
        <TD>-2140405448</TD>
      </TR>

    val dataNode =
      <DATA>
        <TABLEDATA>
          <TR>
            <TD>0.0960165</TD>
            <TD>0.0503736</TD>
            <TD>268435728</TD>
            <TD>-2140405448</TD>
          </TR>
          <TR>
            <TD>0.51784</TD>
            <TD>0.252201</TD>
            <TD>536871168</TD>
            <TD>-2140404569</TD>
          </TR>
        </TABLEDATA>
      </DATA>

    val skyObjects =
      <TABLE>
        <FIELD ID="flags1" datatype="int" name="flags1" ucd="meta.code"/>
        <FIELD ID="umag" datatype="double" name="umag" ucd="phot.mag;em.opt.u"/>
        <FIELD ID="flags2" datatype="int" name="flags2" ucd="meta.code"/>
        <FIELD ID="imag" datatype="double" name="imag" ucd="phot.mag;em.opt.i"/>
        <FIELD ID="decj2000" datatype="double" name="dej2000" ucd="pos.eq.dec;meta.main"/>
        <FIELD ID="raj2000" datatype="double" name="raj2000" ucd="pos.eq.ra;meta.main"/>
        <FIELD ID="rmag" datatype="double" name="rmag" ucd="phot.mag;em.opt.r"/>
        <FIELD ID="objid" datatype="int" name="objid" ucd="meta.id;meta.main"/>
        <FIELD ID="gmag" datatype="double" name="gmag" ucd="phot.mag;em.opt.g"/>
        <FIELD ID="zmag" datatype="double" name="zmag" ucd="phot.mag;em.opt.z"/>
        <FIELD ID="type" datatype="int" name="type" ucd="meta.code"/>
        <FIELD ID="ppmxl" datatype="int" name="ppmxl" ucd="meta.id;meta.main"/>
        <DATA>
          <TABLEDATA>
            <TR>
              <TD>268435728</TD>
              <TD>23.0888</TD>
              <TD>8208</TD>
              <TD>20.3051</TD>
              <TD>0.209323681906</TD>
              <TD>359.745951955</TD>
              <TD>20.88</TD>
              <TD>-2140405448</TD>
              <TD>22.082</TD>
              <TD>19.8812</TD>
              <TD>3</TD>
              <TD>-2140405448</TD>
            </TR>
            <TR>
              <TD>536871168</TD>
              <TD>23.0853</TD>
              <TD>65552</TD>
              <TD>20.7891</TD>
              <TD>0.210251239819</TD>
              <TD>359.749274134</TD>
              <TD>21.7686</TD>
              <TD>-2140404569</TD>
              <TD>23.0889</TD>
              <TD>20.0088</TD>
              <TD>3</TD>
              <TD>-2140404569</TD>
            </TR>
          </TABLEDATA>
      </DATA>
    </TABLE>

    val skyObjectsWithErrors =
      <TABLE>
        <FIELD ID="gmag_err" datatype="double" name="gmag_err" ucd="stat.error;phot.mag;em.opt.g"/>
        <FIELD ID="rmag_err" datatype="double" name="rmag_err" ucd="stat.error;phot.mag;em.opt.r"/>
        <FIELD ID="flags1" datatype="int" name="flags1" ucd="meta.code"/>
        <FIELD ID="umag" datatype="double" name="umag" ucd="phot.mag;em.opt.u"/>
        <FIELD ID="flags2" datatype="int" name="flags2" ucd="meta.code"/>
        <FIELD ID="imag" datatype="double" name="imag" ucd="phot.mag;em.opt.i"/>
        <FIELD ID="zmag_err" datatype="double" name="zmag_err" ucd="stat.error;phot.mag;em.opt.z"/>
        <FIELD ID="decj2000" datatype="double" name="dej2000" ucd="pos.eq.dec;meta.main"/>
        <FIELD ID="umag_err" datatype="double" name="umag_err" ucd="stat.error;phot.mag;em.opt.u"/>
        <FIELD ID="imag_err" datatype="double" name="imag_err" ucd="stat.error;phot.mag;em.opt.i"/>
        <FIELD ID="raj2000" datatype="double" name="raj2000" ucd="pos.eq.ra;meta.main"/>
        <FIELD ID="rmag" datatype="double" name="rmag" ucd="phot.mag;em.opt.r"/>
        <FIELD ID="objid" datatype="int" name="objid" ucd="meta.id;meta.main"/>
        <FIELD ID="gmag" datatype="double" name="gmag" ucd="phot.mag;em.opt.g"/>
        <FIELD ID="zmag" datatype="double" name="zmag" ucd="phot.mag;em.opt.z"/>
        <FIELD ID="type" datatype="int" name="type" ucd="meta.code"/>
        <FIELD ID="jmag" datatype="double" name="jmag" ucd="phot.mag;em.IR.J"/>
        <FIELD ID="e_jmag" datatype="double" name="e_jmag" ucd="stat.error;phot.mag;em.IR.J"/>
        <FIELD ID="ppmxl" datatype="int" name="ppmxl" ucd="meta.id;meta.main"/>
        <DATA>
          <TABLEDATA>
            <TR>
              <TD>0.0960165</TD>
              <TD>0.0503736</TD>
              <TD>268435728</TD>
              <TD>23.0888</TD>
              <TD>8208</TD>
              <TD>20.3051</TD>
              <TD>0.138202</TD>
              <TD>0.209323681906</TD>
              <TD>0.518214</TD>
              <TD>0.0456069</TD>
              <TD>359.745951955</TD>
              <TD>20.88</TD>
              <TD>-2140405448</TD>
              <TD>22.082</TD>
              <TD>19.8812</TD>
              <TD>3</TD>
              <TD>13.74</TD>
              <TD>0.029999999999999999</TD>
              <TD>-2140405448</TD>
            </TR>
            <TR>
              <TD>0.51784</TD>
              <TD>0.252201</TD>
              <TD>536871168</TD>
              <TD>23.0853</TD>
              <TD>65552</TD>
              <TD>20.7891</TD>
              <TD>0.35873</TD>
              <TD>0.210251239819</TD>
              <TD>1.20311</TD>
              <TD>0.161275</TD>
              <TD>359.749274134</TD>
              <TD>21.7686</TD>
              <TD>-2140404569</TD>
              <TD>23.0889</TD>
              <TD>20.0088</TD>
              <TD>3</TD>
              <TD>12.023</TD>
              <TD>0.02</TD>
              <TD>-2140404569</TD>
            </TR>
          </TABLEDATA>
      </DATA>
    </TABLE>

    val skyObjectsWithProperMotion =
      <TABLE>
        <FIELD ID="pmde" datatype="double" name="pmde" ucd="pos.pm;pos.eq.dec"/>
        <FIELD ID="pmra" datatype="double" name="pmra" ucd="pos.pm;pos.eq.ra"/>
        <FIELD ID="dej2000" datatype="double" name="dej2000" ucd="pos.eq.dec;meta.main"/>
        <FIELD ID="epde" datatype="double" name="epde" ucd="time.epoch"/>
        <FIELD ID="raj2000" datatype="double" name="raj2000" ucd="pos.eq.ra;meta.main"/>
        <FIELD ID="rmag" datatype="double" name="rmag" ucd="phot.mag;em.opt.R"/>
        <FIELD ID="e_vmag" datatype="double" name="e_vmag" ucd="stat.error;phot.mag;em.opt.V"/>
        <FIELD ID="e_pmra" datatype="double" name="e_pmra" ucd="stat.error;pos.pm;pos.eq.ra"/>
        <FIELD ID="ucac4" arraysize="*" datatype="char" name="ucac4" ucd="meta.id;meta.main"/>
        <FIELD ID="epra" datatype="double" name="epra" ucd="time.epoch"/>
        <FIELD ID="e_pmde" datatype="double" name="e_pmde" ucd="stat.error;pos.pm;pos.eq.dec"/>
        <DATA>
         <TABLEDATA>
          <TR>
           <TD>-4.9000000000000004</TD>
           <TD>-10.199999999999999</TD>
           <TD>19.9887894444444</TD>
           <TD>2000.3499999999999</TD>
           <TD>9.8971419444444404</TD>
           <TD>14.76</TD>
           <TD>0.02</TD>
           <TD>3.8999999999999999</TD>
           <TD>550-001323</TD>
           <TD>1999.9100000000001</TD>
            <TD>2.4345</TD>
          </TR>
          <TR>
           <TD>-13.9</TD>
           <TD>-7</TD>
           <TD>19.997709722222201</TD>
           <TD>2000.0699999999999</TD>
           <TD>9.9195805555555605</TD>
           <TD>12.983000000000001</TD>
           <TD>0.029999999999999999</TD>
           <TD>1.8</TD>
           <TD>550-001324</TD>
           <TD>1999.4300000000001</TD>
           <TD>2.3999999999999999</TD>
          </TR>
         </TABLEDATA>
        </DATA>
       </TABLE>

    val voTable =
      <VOTABLE>
        <RESOURCE type="results">
          {skyObjects}
        </RESOURCE>
      </VOTABLE>

    val voTableWithErrors =
      <VOTABLE>
        <RESOURCE type="results">
          {skyObjectsWithErrors}
        </RESOURCE>
      </VOTABLE>

    val voTableWithProperMotion =
      <VOTABLE>
        <RESOURCE type="results">
          {skyObjectsWithProperMotion}
        </RESOURCE>
      </VOTABLE>

    "be able to parse empty ucds" in {
      Ucd.parseUcd("") should beEqualTo(Ucd(List()))
    }
    "be able to parse single token ucds" in {
      Ucd.parseUcd("meta.code") should beEqualTo(Ucd(List(UcdWord("meta.code"))))
    }
    "be able to parse multi token ucds and preserve order" in {
      Ucd.parseUcd("stat.error;phot.mag;em.opt.g") should beEqualTo(Ucd(List(UcdWord("stat.error"), UcdWord("phot.mag"), UcdWord("em.opt.g"))))
    }
    "be able to parse be case-insensitive, converting to lower case" in {
      Ucd.parseUcd("STAT.Error;EM.opt.G") should beEqualTo(Ucd("stat.error;em.opt.g"))
    }
    "be able to parse a field definition" in {
      val fieldXml = <FIELD ID="gmag_err" datatype="double" name="gmag_err" ucd="stat.error;phot.mag;em.opt.g"/>
      parseFieldDescriptor(fieldXml) should beSome(FieldDescriptor(FieldId("gmag_err", Ucd("stat.error;phot.mag;em.opt.g")), "gmag_err"))
      // Empty field
      parseFieldDescriptor(<FIELD/>) should beNone
      // non field xml
      parseFieldDescriptor(<TAG/>) should beNone
      // missing attributes
      parseFieldDescriptor(<FIELD ID="abc"/>) should beNone
    }
    "be able to parse a list of fields" in {
      val result =
        FieldDescriptor(FieldId("gmag_err", Ucd("stat.error;phot.mag;em.opt.g")), "gmag_err") ::
        FieldDescriptor(FieldId("rmag_err", Ucd("stat.error;phot.mag;em.opt.r")), "rmag_err") ::
        FieldDescriptor(FieldId("flags1", Ucd("meta.code")), "flags1") ::
        FieldDescriptor(FieldId("ppmxl", Ucd("meta.id;meta.main")), "ppmxl") :: Nil

      parseFields(fieldsNode) should beEqualTo(result)
    }
    "be able to parse a data  row with a list of fields" in {
      val fields = parseFields(fieldsNode)

      val result = TableRow(
        TableRowItem(FieldDescriptor(FieldId("gmag_err", Ucd("stat.error;phot.mag;em.opt.g")), "gmag_err"), "0.0960165") ::
        TableRowItem(FieldDescriptor(FieldId("rmag_err", Ucd("stat.error;phot.mag;em.opt.r")), "rmag_err"), "0.0503736") ::
        TableRowItem(FieldDescriptor(FieldId("flags1", Ucd("meta.code")), "flags1"), "268435728") ::
        TableRowItem(FieldDescriptor(FieldId("ppmxl", Ucd("meta.id;meta.main")), "ppmxl"), "-2140405448") :: Nil
      )
      parseTableRow(fields, tableRow) should beEqualTo(result)
    }
    "be able to parse a list of rows with a list of fields" in {
      val fields = parseFields(fieldsNode)

      val result = List(
        TableRow(
          TableRowItem(FieldDescriptor(FieldId("gmag_err", Ucd("stat.error;phot.mag;em.opt.g")), "gmag_err"), "0.0960165") ::
          TableRowItem(FieldDescriptor(FieldId("rmag_err", Ucd("stat.error;phot.mag;em.opt.r")), "rmag_err"), "0.0503736") ::
          TableRowItem(FieldDescriptor(FieldId("flags1", Ucd("meta.code")), "flags1"), "268435728") ::
          TableRowItem(FieldDescriptor(FieldId("ppmxl", Ucd("meta.id;meta.main")), "ppmxl"), "-2140405448") :: Nil
        ),
        TableRow(
          TableRowItem(FieldDescriptor(FieldId("gmag_err", Ucd("stat.error;phot.mag;em.opt.g")), "gmag_err"), "0.51784") ::
          TableRowItem(FieldDescriptor(FieldId("rmag_err", Ucd("stat.error;phot.mag;em.opt.r")), "rmag_err"), "0.252201") ::
          TableRowItem(FieldDescriptor(FieldId("flags1", Ucd("meta.code")), "flags1"), "536871168") ::
          TableRowItem(FieldDescriptor(FieldId("ppmxl", Ucd("meta.id;meta.main")), "ppmxl"), "-2140404569") :: Nil
        ))
      parseTableRows(fields, dataNode) should beEqualTo(result)
    }
    "be able to convert a TableRow into a SiderealTarget" in {
      val fields = parseFields(fieldsNode)

      val validRow = TableRow(
                TableRowItem(FieldDescriptor(FieldId("ppmxl", Ucd("meta.id;meta.main")), "ppmxl"), "123456") ::
                TableRowItem(FieldDescriptor(FieldId("decj2000", Ucd("pos.eq.dec;meta.main")),"dej2000"), "0.209323681906") ::
                TableRowItem(FieldDescriptor(FieldId("raj2000", Ucd("pos.eq.ra;meta.main")), "raj2000"), "359.745951955") :: Nil
              )
      tableRow2Target(None, fields)(validRow) should beEqualTo(\/-(SiderealTarget("123456", Coordinates(RightAscension.fromAngle(Angle.parseDegrees("359.745951955").getOrElse(Angle.zero)), Declination.fromAngle(Angle.parseDegrees("0.209323681906").getOrElse(Angle.zero)).getOrElse(Declination.zero)), None, None, None, Nil)))

      val rowWithMissingId = TableRow(
                TableRowItem(FieldDescriptor(FieldId("decj2000", Ucd("pos.eq.dec;meta.main")), "dej2000"), "0.209323681906") ::
                TableRowItem(FieldDescriptor(FieldId("raj2000", Ucd("pos.eq.ra;meta.main")), "raj2000"), "359.745951955") :: Nil
              )
      tableRow2Target(None, fields)(rowWithMissingId) should beEqualTo(-\/(MissingValue(FieldId("ppmxl", VoTableParser.UCD_OBJID))))

      val rowWithBadRa = TableRow(
                TableRowItem(FieldDescriptor(FieldId("ppmxl", Ucd("meta.id;meta.main")), "ppmxl"), "123456") ::
                TableRowItem(FieldDescriptor(FieldId("decj2000", Ucd("pos.eq.dec;meta.main")), "dej2000"), "0.209323681906") ::
                TableRowItem(FieldDescriptor(FieldId("raj2000", Ucd("pos.eq.ra;meta.main")), "raj2000"), "ABC") :: Nil
            )
      tableRow2Target(None, fields)(rowWithBadRa) should beEqualTo(-\/(FieldValueProblem(VoTableParser.UCD_RA, "ABC")))
    }
    "be able to parse magnitude bands in PPMXL" in {
      val iMagField = Ucd("phot.mag;em.opt.i")
      // Optical band
      PPMXLAdapter.parseMagnitude((FieldId("id", iMagField), "20.3051")) should beEqualTo(\/-((FieldId("id", iMagField), MagnitudeBand.I, 20.3051)))

      val jIRMagField = Ucd("phot.mag;em.IR.J")
      // IR band
      PPMXLAdapter.parseMagnitude((FieldId("id", jIRMagField), "13.2349")) should beEqualTo(\/-((FieldId("id", jIRMagField), MagnitudeBand.J, 13.2349)))

      val jIRErrMagField = Ucd("stat.error;phot.mag;em.IR.J")
      // IR Error
      PPMXLAdapter.parseMagnitude((FieldId("id", jIRErrMagField), "0.02")) should beEqualTo(\/-((FieldId("id", jIRErrMagField), MagnitudeBand.J, 0.02)))

      // No magnitude field
      val badField = Ucd("meta.name")
      PPMXLAdapter.parseMagnitude((FieldId("id", badField), "id")) should beEqualTo(-\/(UnmatchedField(badField)))

      // Bad value
      PPMXLAdapter.parseMagnitude((FieldId("id", iMagField), "stringValue")) should beEqualTo(-\/(FieldValueProblem(iMagField, "stringValue")))

      // Unknown magnitude
      val noBandField = Ucd("phot.mag;em.opt.p")
      PPMXLAdapter.parseMagnitude((FieldId("id", noBandField), "stringValue")) should beEqualTo(-\/(UnmatchedField(noBandField)))
    }
    "be able to map sloan magnitudes in UCAC4, OCSADV-245" in {
      val gMagField = Ucd("phot.mag;em.opt.R")
      // gmag maps to g'
      UCAC4Adapter.parseMagnitude((FieldId("gmag", gMagField), "20.3051")) should beEqualTo(\/-((FieldId("gmag", gMagField), MagnitudeBand._g, 20.3051)))

      val rMagField = Ucd("phot.mag;em.opt.R")
      // rmag maps to r'
      UCAC4Adapter.parseMagnitude((FieldId("rmag", rMagField), "20.3051")) should beEqualTo(\/-((FieldId("rmag", rMagField), MagnitudeBand._r, 20.3051)))

      val iMagField = Ucd("phot.mag;em.opt.I")
      // imag maps to r'
      UCAC4Adapter.parseMagnitude((FieldId("imag", iMagField), "20.3051")) should beEqualTo(\/-((FieldId("imag", iMagField), MagnitudeBand._i, 20.3051)))
    }
    "be able to map sloan magnitudes in Simbad" in {
      val zMagField = Ucd("phot.mag;em.opt.I")
      // FLUX_z maps to z'
      SimbadAdapter.parseMagnitude((FieldId("FLUX_z", zMagField), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_z", zMagField), MagnitudeBand._z, 20.3051)))

      val rMagField = Ucd("phot.mag;em.opt.R")
      // FLUX_r maps to r'
      SimbadAdapter.parseMagnitude((FieldId("FLUX_r", rMagField), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_r", rMagField), MagnitudeBand._r, 20.3051)))

      val uMagField = Ucd("phot.mag;em.opt.u")
      // FLUX_u maps to u'
      SimbadAdapter.parseMagnitude((FieldId("FLUX_u", uMagField), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_u", uMagField), MagnitudeBand._u, 20.3051)))

      val gMagField = Ucd("phot.mag;em.opt.b")
      // FLUX_g maps to g'
      SimbadAdapter.parseMagnitude((FieldId("FLUX_g", gMagField), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_g", gMagField), MagnitudeBand._g, 20.3051)))

      val iMagField = Ucd("phot.mag;em.opt.i")
      // FLUX_u maps to u'
      SimbadAdapter.parseMagnitude((FieldId("FLUX_i", iMagField), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_i", iMagField), MagnitudeBand._i, 20.3051)))
    }
    "be able to map non-sloan magnitudes in Simbad" in {
      val rMagField = Ucd("phot.mag;em.opt.R")
      // FLUX_R maps to R
      SimbadAdapter.parseMagnitude((FieldId("FLUX_R", rMagField), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_R", rMagField), MagnitudeBand.R, 20.3051)))

      val uMagField = Ucd("phot.mag;em.opt.U")
      // FLUX_U maps to U
      SimbadAdapter.parseMagnitude((FieldId("FLUX_U", uMagField), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_U", uMagField), MagnitudeBand.U, 20.3051)))

      val iMagField = Ucd("phot.mag;em.opt.I")
      // FLUX_I maps to I
      SimbadAdapter.parseMagnitude((FieldId("FLUX_I", iMagField), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_I", iMagField), MagnitudeBand.I, 20.3051)))
    }
    "be able to map magnitude errors in Simbad" in {
      // Magnitude errors in simbad don't include the band in the UCD, we must get it from the ID :(
      val magErrorUcd = Ucd("stat.error;phot.mag")
      // FLUX_r maps to r'
      SimbadAdapter.parseMagnitude((FieldId("FLUX_ERROR_r", magErrorUcd), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_ERROR_r", magErrorUcd), MagnitudeBand._r, 20.3051)))

      // FLUX_R maps to R
      SimbadAdapter.parseMagnitude((FieldId("FLUX_ERROR_R", magErrorUcd), "20.3051")) should beEqualTo(\/-((FieldId("FLUX_ERROR_R", magErrorUcd), MagnitudeBand.R, 20.3051)))
    }
    "be able to parse an xml into a list of SiderealTargets list of rows with a list of fields" in {
      val magsTarget1 = List(new Magnitude(23.0888, MagnitudeBand.U), new Magnitude(22.082, MagnitudeBand._g), new Magnitude(20.88, MagnitudeBand.R), new Magnitude(20.3051, MagnitudeBand.I), new Magnitude(19.8812, MagnitudeBand._z))
      val magsTarget2 = List(new Magnitude(23.0853, MagnitudeBand.U), new Magnitude(23.0889, MagnitudeBand._g), new Magnitude(21.7686, MagnitudeBand.R), new Magnitude(20.7891, MagnitudeBand.I), new Magnitude(20.0088, MagnitudeBand._z))

      val result = ParsedTable(List(
        \/-(SiderealTarget("-2140405448", Coordinates(RightAscension.fromDegrees(359.745951955), Declination.fromAngle(Angle.parseDegrees("0.209323681906").getOrElse(Angle.zero)).getOrElse(Declination.zero)), None, None, None, magsTarget1)),
        \/-(SiderealTarget("-2140404569", Coordinates(RightAscension.fromDegrees(359.749274134), Declination.fromAngle(Angle.parseDegrees("0.210251239819").getOrElse(Angle.zero)).getOrElse(Declination.zero)), None, None, None, magsTarget2))
      ))
      // There is only one table
      parse(voTable).tables.head should beEqualTo(result)
      parse(voTable).tables.head.containsError should beFalse
    }
    "be able to parse an xml into a list of SiderealTargets including magnitude errors" in {
      val magsTarget1 = List(new Magnitude(23.0888, MagnitudeBand.U, 0.518214), new Magnitude(22.082, MagnitudeBand._g, 0.0960165), new Magnitude(20.88, MagnitudeBand.R, 0.0503736), new Magnitude(20.3051, MagnitudeBand.I, 0.0456069), new Magnitude(19.8812, MagnitudeBand._z, 0.138202), new Magnitude(13.74, MagnitudeBand.J, 0.03))
      val magsTarget2 = List(new Magnitude(23.0853, MagnitudeBand.U, 1.20311), new Magnitude(23.0889, MagnitudeBand._g, 0.51784), new Magnitude(21.7686, MagnitudeBand.R, 0.252201), new Magnitude(20.7891, MagnitudeBand.I, 0.161275), new Magnitude(20.0088, MagnitudeBand._z, 0.35873), new Magnitude(12.023, MagnitudeBand.J, 0.02))

      val result = ParsedTable(List(
        \/-(SiderealTarget("-2140405448", Coordinates(RightAscension.fromDegrees(359.745951955), Declination.fromAngle(Angle.parseDegrees("0.209323681906").getOrElse(Angle.zero)).getOrElse(Declination.zero)), None, None, None, magsTarget1)),
        \/-(SiderealTarget("-2140404569", Coordinates(RightAscension.fromDegrees(359.749274134), Declination.fromAngle(Angle.parseDegrees("0.210251239819").getOrElse(Angle.zero)).getOrElse(Declination.zero)), None, None, None, magsTarget2))
      ))
      parse(voTableWithErrors).tables.head should beEqualTo(result)
    }
    "be able to parse an xml into a list of SiderealTargets including proper motion" in {
      val magsTarget1 = List(new Magnitude(14.76, MagnitudeBand._r, MagnitudeSystem.AB))
      val magsTarget2 = List(new Magnitude(12.983, MagnitudeBand._r, MagnitudeSystem.AB))
      val pm1 = ProperMotion(RightAscensionAngularVelocity(AngularVelocity(-10.199999999999999)), DeclinationAngularVelocity(AngularVelocity(-4.9000000000000004))).some
      val pm2 = ProperMotion(RightAscensionAngularVelocity(AngularVelocity(-7)), DeclinationAngularVelocity(AngularVelocity(-13.9))).some

      val result = ParsedTable(List(
        \/-(SiderealTarget("550-001323", Coordinates(RightAscension.fromDegrees(9.897141944444456), Declination.fromAngle(Angle.parseDegrees("19.98878944444442").getOrElse(Angle.zero)).getOrElse(Declination.zero)), pm1, None, None, magsTarget1)),
        \/-(SiderealTarget("550-001324", Coordinates(RightAscension.fromDegrees(9.91958055555557), Declination.fromAngle(Angle.parseDegrees("19.997709722222226").getOrElse(Angle.zero)).getOrElse(Declination.zero)), pm2, None, None, magsTarget2))
      ))
      parse(voTableWithProperMotion).tables.head should beEqualTo(result)
    }
    "be able to validate and parse an xml from sds9" in {
      val badXml = "votable-non-validating.xml"
      VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$badXml")) should beEqualTo(-\/(ValidationError(UCAC4)))
    }
    "be able to detect unknown catalogs" in {
      val xmlFile = "votable-unknown.xml"
      val result  = VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$xmlFile"))
      result.map { parsed =>
        parsed.containsError must beEqualTo(true)
        parsed.tables.map { table =>
          table.containsError must beEqualTo(true)
          table.rows.map(_ must beEqualTo(-\/(UnknownCatalog)))
        }
      }
      result.isRight must beEqualTo(true)
    }
    "be able to validate and parse an xml from ucac4" in {
      val xmlFile = "votable-ucac4.xml"
      VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$xmlFile")).map(_.tables.forall(!_.containsError)) must beEqualTo(\/.right(true))
      VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables should be size 1
    }
    "be able to validate and parse an xml from ppmxl" in {
      val xmlFile = "votable-ppmxl.xml"
      VoTableParser.parse(PPMXL, getClass.getResourceAsStream(s"/$xmlFile")).map(_.tables.forall(!_.containsError)) must beEqualTo(\/.right(true))
      VoTableParser.parse(PPMXL, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables should be size 1
    }
    "be able to select r1mag over r2mag and b2mag when b1mag is absent in ppmxl" in {
      val xmlFile = "votable-ppmxl.xml"
      val result = VoTableParser.parse(PPMXL, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables.map(TargetsTable.apply).map(_.rows).flatMap(_.find(_.name == "-1471224894")).headOption

      val magR = result >>= {_.magnitudeIn(MagnitudeBand.R)}
      magR.map(_.value) should beSome(18.149999999999999)
      val magB = result >>= {_.magnitudeIn(MagnitudeBand.B)}
      magB.map(_.value) should beSome(17.109999999999999)
    }
    "be able to ignore bogus magnitudes on ppmxl" in {
      val xmlFile = "votable-ppmxl.xml"
      // Check a well-known target containing invalid magnitude values an bands H, I, K and J
      val result = VoTableParser.parse(PPMXL, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables.map(TargetsTable.apply).map(_.rows).flatMap(_.find(_.name == "-1471224894")).headOption
      val magH = result >>= {_.magnitudeIn(MagnitudeBand.H)}
      val magI = result >>= {_.magnitudeIn(MagnitudeBand.I)}
      val magK = result >>= {_.magnitudeIn(MagnitudeBand.K)}
      val magJ = result >>= {_.magnitudeIn(MagnitudeBand.J)}
      magH should beNone
      magI should beNone
      magK should beNone
      magJ should beNone
    }
    "be able to filter out bad magnitudes" in {
      val xmlFile = "fmag.xml"
      VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$xmlFile")).map(_.tables.forall(!_.containsError)) must beEqualTo(\/.right(true))
      // The sample has only one row
      val result = VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables.headOption.flatMap(_.rows.headOption).get

      val mags = result.map(_.magnitudeIn(MagnitudeBand.R))
      // Does not contain R as it is filtered out being magnitude 20 and error 99
      mags should beEqualTo(\/.right(None))
    }
    "convert fmag to UC" in {
      val xmlFile = "fmag.xml"
      VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$xmlFile")).map(_.tables.forall(!_.containsError)) must beEqualTo(\/.right(true))
      // The sample has only one row
      val result = VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables.headOption.flatMap(_.rows.headOption).get

      val mags = result.map(_.magnitudeIn(MagnitudeBand.UC))
      // Fmag gets converted to UC
      mags should beEqualTo(\/.right(Some(Magnitude(5.9, MagnitudeBand.UC, None, MagnitudeSystem.VEGA))))
    }
    "extract Sloan's band" in {
      val xmlFile = "sloan.xml"
      // The sample has only one row
      val result = VoTableParser.parse(UCAC4, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables.headOption.flatMap(_.rows.headOption).get

      val gmag = result.map(_.magnitudeIn(MagnitudeBand._g))
      // gmag gets converted to g'
      gmag should beEqualTo(\/.right(Some(Magnitude(15.0, MagnitudeBand._g, 0.39.some, MagnitudeSystem.AB))))
      val rmag = result.map(_.magnitudeIn(MagnitudeBand._r))
      // rmag gets converted to r'
      rmag should beEqualTo(\/.right(Some(Magnitude(13.2, MagnitudeBand._r, 0.5.some, MagnitudeSystem.AB))))
      val imag = result.map(_.magnitudeIn(MagnitudeBand._i))
      // rmag gets converted to r'
      imag should beEqualTo(\/.right(Some(Magnitude(5, MagnitudeBand._i, 0.34.some, MagnitudeSystem.AB))))
    }
    "parse simbad named queries" in {
      val xmlFile = "simbad-vega.xml"
      // The sample has only one row
      val result = VoTableParser.parse(SIMBAD, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables.headOption.flatMap(_.rows.headOption).get

      // id and coordinates
      result.map(_.name) should beEqualTo(\/.right("* alf Lyr"))
      result.map(_.coordinates.ra) should beEqualTo(\/.right(RightAscension.fromAngle(Angle.fromDegrees(279.23473479))))
      result.map(_.coordinates.dec) should beEqualTo(\/.right(Declination.fromAngle(Angle.fromDegrees(38.78368896)).getOrElse(Declination.zero)))
      // proper motions
      result.map(_.properMotion.map(_.deltaRA)) should beEqualTo(\/.right(Some(RightAscensionAngularVelocity(AngularVelocity(200.94)))))
      result.map(_.properMotion.map(_.deltaDec)) should beEqualTo(\/.right(Some(DeclinationAngularVelocity(AngularVelocity(286.23)))))
      // radial velocity
      result.map(_.radialVelocity) should beEqualTo(\/.right(RadialVelocity(KilometersPerSecond(-20.6)).some))
      // magnitudes
      result.map(_.magnitudeIn(MagnitudeBand.U)) should beEqualTo(\/.right(Some(new Magnitude(0.03, MagnitudeBand.U))))
      result.map(_.magnitudeIn(MagnitudeBand.B)) should beEqualTo(\/.right(Some(new Magnitude(0.03, MagnitudeBand.B))))
      result.map(_.magnitudeIn(MagnitudeBand.V)) should beEqualTo(\/.right(Some(new Magnitude(0.03, MagnitudeBand.V))))
      result.map(_.magnitudeIn(MagnitudeBand.R)) should beEqualTo(\/.right(Some(new Magnitude(0.07, MagnitudeBand.R))))
      result.map(_.magnitudeIn(MagnitudeBand.I)) should beEqualTo(\/.right(Some(new Magnitude(0.10, MagnitudeBand.I))))
      result.map(_.magnitudeIn(MagnitudeBand.J)) should beEqualTo(\/.right(Some(new Magnitude(-0.18, MagnitudeBand.J))))
      result.map(_.magnitudeIn(MagnitudeBand.H)) should beEqualTo(\/.right(Some(new Magnitude(-0.03, MagnitudeBand.H))))
      result.map(_.magnitudeIn(MagnitudeBand.K)) should beEqualTo(\/.right(Some(new Magnitude(0.13, MagnitudeBand.K))))
    }
    "parse simbad named queries with sloan magnitudes" in {
      val xmlFile = "simbad-2MFGC6625.xml"
      // The sample has only one row
      val result = VoTableParser.parse(SIMBAD, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables.headOption.flatMap(_.rows.headOption).get

      // id and coordinates
      result.map(_.name) should beEqualTo(\/.right("2MFGC 6625"))
      result.map(_.coordinates.ra) should beEqualTo(\/.right(RightAscension.fromAngle(Angle.fromHMS(8, 23, 54.966).getOrElse(Angle.zero))))
      result.map(_.coordinates.dec) should beEqualTo(\/.right(Declination.fromAngle(Angle.fromDMS(28, 6, 21.6792).getOrElse(Angle.zero)).getOrElse(Declination.zero)))
      // proper motions
      result.map(_.properMotion) should beEqualTo(\/.right(None))
      // radial velocity
      result.map(_.radialVelocity) should beEqualTo(\/.right(RadialVelocity(KilometersPerSecond(13828)).some))
      // magnitudes
      result.map(_.magnitudeIn(MagnitudeBand._u)) should beEqualTo(\/.right(Some(new Magnitude(17.353, MagnitudeBand._u, 0.009))))
      result.map(_.magnitudeIn(MagnitudeBand._g)) should beEqualTo(\/.right(Some(new Magnitude(16.826, MagnitudeBand._g, 0.004))))
      result.map(_.magnitudeIn(MagnitudeBand._r)) should beEqualTo(\/.right(Some(new Magnitude(17.286, MagnitudeBand._r, 0.005))))
      result.map(_.magnitudeIn(MagnitudeBand._i)) should beEqualTo(\/.right(Some(new Magnitude(16.902, MagnitudeBand._i, 0.005))))
      result.map(_.magnitudeIn(MagnitudeBand._z)) should beEqualTo(\/.right(Some(new Magnitude(17.015, MagnitudeBand._z, 0.011))))
    }
    "parse simbad named queries with mixed magnitudes" in {
      val xmlFile = "simbad-J000008.13.xml"
      // The sample has only one row
      val result = VoTableParser.parse(SIMBAD, getClass.getResourceAsStream(s"/$xmlFile")).getOrElse(ParsedVoResource(Nil)).tables.headOption.flatMap(_.rows.headOption).get

      // id and coordinates
      result.map(_.name) should beEqualTo(\/.right("2SLAQ J000008.13+001634.6"))
      result.map(_.coordinates.ra) should beEqualTo(\/.right(RightAscension.fromAngle(Angle.fromHMS(0, 0, 8.136).getOrElse(Angle.zero))))
      result.map(_.coordinates.dec) should beEqualTo(\/.right(Declination.fromAngle(Angle.fromDMS(0, 16, 34.6908).getOrElse(Angle.zero)).getOrElse(Declination.zero)))
      // proper motions
      result.map(_.properMotion) should beEqualTo(\/.right(None))
      // radial velocity
      result.map(_.radialVelocity) should beEqualTo(\/.right(RadialVelocity(KilometersPerSecond(233509)).some))
      // magnitudes
      result.map(_.magnitudeIn(MagnitudeBand.B)) should beEqualTo(\/.right(Some(new Magnitude(20.35, MagnitudeBand.B))))
      result.map(_.magnitudeIn(MagnitudeBand.V)) should beEqualTo(\/.right(Some(new Magnitude(20.03, MagnitudeBand.V))))
      result.map(_.magnitudeIn(MagnitudeBand.J)) should beEqualTo(\/.right(Some(new Magnitude(19.399, MagnitudeBand.J, 0.073))))
      result.map(_.magnitudeIn(MagnitudeBand.H)) should beEqualTo(\/.right(Some(new Magnitude(19.416, MagnitudeBand.H, 0.137))))
      result.map(_.magnitudeIn(MagnitudeBand.K)) should beEqualTo(\/.right(Some(new Magnitude(19.176, MagnitudeBand.K, 0.115))))
      result.map(_.magnitudeIn(MagnitudeBand._u)) should beEqualTo(\/.right(Some(new Magnitude(20.233, MagnitudeBand._u, 0.054))))
      result.map(_.magnitudeIn(MagnitudeBand._g)) should beEqualTo(\/.right(Some(new Magnitude(20.201, MagnitudeBand._g, 0.021))))
      result.map(_.magnitudeIn(MagnitudeBand._r)) should beEqualTo(\/.right(Some(new Magnitude(19.929, MagnitudeBand._r, 0.021))))
      result.map(_.magnitudeIn(MagnitudeBand._i)) should beEqualTo(\/.right(Some(new Magnitude(19.472, MagnitudeBand._i, 0.023))))
      result.map(_.magnitudeIn(MagnitudeBand._z)) should beEqualTo(\/.right(Some(new Magnitude(19.191, MagnitudeBand._z, 0.068))))
    }
    "parse simbad with a not-found name" in {
      val xmlFile = "simbad-not-found.xml"
      // Simbad returns non-valid xml when an element is not found, we need to skip validation :S
      val result = VoTableParser.parse(SIMBAD, getClass.getResourceAsStream(s"/$xmlFile"), checkValidity = false)
      result must beEqualTo(\/.right(ParsedVoResource(List())))
    }
  }
}
