package sinlin.data.odt;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: art
 * Date: 2/29/16
 * Time: 1:27 AM
 */
public class OdfDataTest {
    OdfData odfData;

    @Before
    public void setUp() throws Exception {
        odfData = new OdfData(
                "src/test/resources/fn_test.ods");
    }

    @After
    public void tearDown() throws Exception {
        odfData = null;
    }

    @Test
    public void testGetRow() throws Exception {
        Assert.assertEquals(
                "[ten1, ten2, ten3, ten4, ten5, ten6, ten7, ten8, ten9, ten10]",
                odfData.getRow("ten").toString());
        Assert.assertEquals(
                "[ten_sheet2_1, ten_sheet2_2, ten_sheet2_3, ten_sheet2_4, ten_sheet2_5, ten_sheet2_6, ten_sheet2_7, ten_sheet2_8, ten_sheet2_9, ten_sheet2_10]",
                odfData.getRow("ten_sheet2").toString());
        Assert.assertEquals(
                "[tenXten11, tenXten12, tenXten13, tenXten14, tenXten15, tenXten16, tenXten17, tenXten18, tenXten19, tenXten20, tenXten21, tenXten22, tenXten23, tenXten24, tenXten25, tenXten26, tenXten27, tenXten28, tenXten29, tenXten30, tenXten31, tenXten32, tenXten33, tenXten34, tenXten35, tenXten36, tenXten37, tenXten38, tenXten39, tenXten40, tenXten41, tenXten42, tenXten43, tenXten44, tenXten45, tenXten46, tenXten47, tenXten48, tenXten49, tenXten50, tenXten51, tenXten52, tenXten53, tenXten54, tenXten55, tenXten56, tenXten57, tenXten58, tenXten59, tenXten60, tenXten61, tenXten62, tenXten63, tenXten64, tenXten65, tenXten66, tenXten67, tenXten68, tenXten69, tenXten70, tenXten71, tenXten72, tenXten73, tenXten74, tenXten75, tenXten76, tenXten77, tenXten78, tenXten79, tenXten80, tenXten81, tenXten82, tenXten83, tenXten84, tenXten85, tenXten86, tenXten87, tenXten88, tenXten89, tenXten90, tenXten91, tenXten92, tenXten93, tenXten94, tenXten95, tenXten96, tenXten97, tenXten98, tenXten99, tenXten100, tenXten101, tenXten102, tenXten103, tenXten104, tenXten105, tenXten106, tenXten107, tenXten108, tenXten109, tenXten110]",
                odfData.getRow("tenXten").toString());
        Assert.assertEquals(
                "[tenXten_sheet2_11, tenXten_sheet2_12, tenXten_sheet2_13, tenXten_sheet2_14, tenXten_sheet2_15, tenXten_sheet2_16, tenXten_sheet2_17, tenXten_sheet2_18, tenXten_sheet2_19, tenXten_sheet2_20, tenXten_sheet2_21, tenXten_sheet2_22, tenXten_sheet2_23, tenXten_sheet2_24, tenXten_sheet2_25, tenXten_sheet2_26, tenXten_sheet2_27, tenXten_sheet2_28, tenXten_sheet2_29, tenXten_sheet2_30, tenXten_sheet2_31, tenXten_sheet2_32, tenXten_sheet2_33, tenXten_sheet2_34, tenXten_sheet2_35, tenXten_sheet2_36, tenXten_sheet2_37, tenXten_sheet2_38, tenXten_sheet2_39, tenXten_sheet2_40, tenXten_sheet2_41, tenXten_sheet2_42, tenXten_sheet2_43, tenXten_sheet2_44, tenXten_sheet2_45, tenXten_sheet2_46, tenXten_sheet2_47, tenXten_sheet2_48, tenXten_sheet2_49, tenXten_sheet2_50, tenXten_sheet2_51, tenXten_sheet2_52, tenXten_sheet2_53, tenXten_sheet2_54, tenXten_sheet2_55, tenXten_sheet2_56, tenXten_sheet2_57, tenXten_sheet2_58, tenXten_sheet2_59, tenXten_sheet2_60, tenXten_sheet2_61, tenXten_sheet2_62, tenXten_sheet2_63, tenXten_sheet2_64, tenXten_sheet2_65, tenXten_sheet2_66, tenXten_sheet2_67, tenXten_sheet2_68, tenXten_sheet2_69, tenXten_sheet2_70, tenXten_sheet2_71, tenXten_sheet2_72, tenXten_sheet2_73, tenXten_sheet2_74, tenXten_sheet2_75, tenXten_sheet2_76, tenXten_sheet2_77, tenXten_sheet2_78, tenXten_sheet2_79, tenXten_sheet2_80, tenXten_sheet2_81, tenXten_sheet2_82, tenXten_sheet2_83, tenXten_sheet2_84, tenXten_sheet2_85, tenXten_sheet2_86, tenXten_sheet2_87, tenXten_sheet2_88, tenXten_sheet2_89, tenXten_sheet2_90, tenXten_sheet2_91, tenXten_sheet2_92, tenXten_sheet2_93, tenXten_sheet2_94, tenXten_sheet2_95, tenXten_sheet2_96, tenXten_sheet2_97, tenXten_sheet2_98, tenXten_sheet2_99, tenXten_sheet2_100, tenXten_sheet2_101, tenXten_sheet2_102, tenXten_sheet2_103, tenXten_sheet2_104, tenXten_sheet2_105, tenXten_sheet2_106, tenXten_sheet2_107, tenXten_sheet2_108, tenXten_sheet2_109, tenXten_sheet2_110]",
                odfData.getRow("tenXten_sheet2").toString());
//        Assert.assertEquals(
//                "",
//                odfData.getRow("ten_sheet20").toString());//fixme
        Assert.assertEquals(
                "[ten_sheet4_1, ten_sheet4_2, ten_sheet4_3, ten_sheet4_4, ten_sheet4_5, ten_sheet4_6, ten_sheet4_7, ten_sheet4_8, ten_sheet4_9, ten_sheet4_10]",
                odfData.getRow("ten_sheet4").toString());
        Assert.assertEquals(
                "[Xten_1, Xten_2, Xten_3, Xten_4, Xten_5, Xten_6, Xten_7, Xten_8, Xten_9, Xten_10]",
                odfData.getRow("Xten").toString());
        Assert.assertEquals(
                "[Xten_sheet2_1, Xten_sheet2_2, Xten_sheet2_3, Xten_sheet2_4, Xten_sheet2_5, Xten_sheet2_6, Xten_sheet2_7, Xten_sheet2_8, Xten_sheet2_9, Xten_sheet2_10]",
                odfData.getRow("Xten_sheet2").toString());
        Assert.assertEquals(
                "[_q, _w, _e, _r, _t]",
                odfData.getRow("_").toString());
//        Assert.assertEquals(
//                "",
//                odfData.getRow("intersect").toString());//fixme
//        Assert.assertEquals(
//                "",
//                odfData.getRow("intersect1").toString());//fixme
//        Assert.assertEquals(
//                "",
//                odfData.getRow("intersect_steet2").toString());//fixme
//        Assert.assertEquals(
//                "",
//                odfData.getRow("intersect1_steet2").toString());//fixme
        Assert.assertEquals(
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 492, 493, 494, 495, 496, 497, 498, 499, 500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 526, 527, 528, 529, 530, 531, 532, 533, 534, 535, 536, 537, 538, 539, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549, 550, 551, 552, 553, 554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 567, 568, 569, 570, 571, 572, 573, 574, 575, 576, 577, 578, 579, 580, 581, 582, 583, 584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597, 598, 599, 600, 601, 602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629, 630, 631, 632, 633, 634, 635, 636, 637, 638, 639, 640, 641, 642, 643, 644, 645, 646, 647, 648, 649, 650, 651, 652, 653, 654, 655, 656, 657, 658, 659, 660, 661, 662, 663, 664, 665, 666, 667, 668, 669, 670, 671, 672, 673, 674, 675, 676, 677, 678, 679, 680, 681, 682, 683, 684, 685, 686, 687, 688, 689, 690, 691, 692, 693, 694, 695, 696, 697, 698, 699, 700, 701, 702, 703, 704, 705, 706, 707, 708, 709, 710, 711, 712, 713, 714, 715, 716, 717, 718, 719, 720, 721, 722, 723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735, 736, 737, 738, 739, 740, 741, 742, 743, 744, 745, 746, 747, 748, 749, 750, 751, 752, 753, 754, 755, 756, 757, 758, 759, 760, 761, 762, 763, 764, 765, 766, 767, 768, 769, 770, 771, 772, 773, 774, 775, 776, 777, 778, 779, 780, 781, 782, 783, 784, 785, 786, 787, 788, 789, 790, 791, 792, 793, 794, 795, 796, 797, 798, 799, 800, 801, 802, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812, 813, 814, 815, 816, 817, 818, 819, 820, 821, 822, 823, 824, 825, 826, 827, 828, 829, 830, 831, 832, 833, 834, 835, 836, 837, 838, 839, 840, 841, 842, 843, 844, 845, 846, 847, 848, 849, 850, 851, 852, 853, 854, 855, 856, 857, 858, 859, 860, 861, 862, 863, 864, 865, 866, 867, 868, 869, 870, 871, 872, 873, 874, 875, 876, 877, 878, 879, 880, 881, 882, 883, 884, 885, 886, 887, 888, 889, 890, 891, 892, 893, 894, 895, 896, 897, 898, 899, 900, 901]",
                odfData.getRow("ninehundred").toString());
//        Assert.assertEquals(
//                "one",
//                odfData.getRow("one").toString());//fixme
    }

    @Test
    public void testPrintRanges() throws Exception {

    }
}
