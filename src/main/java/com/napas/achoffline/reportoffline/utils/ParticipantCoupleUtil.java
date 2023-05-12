/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.utils;

import com.napas.achoffline.reportoffline.define.FundTransferSystem;

/**
 *
 * @author huynx
 */
public class ParticipantCoupleUtil {

    public static class ParticipantCouple {

        public String debtorAgent;
        public String creditorAgent;
    }

    public static ParticipantCouple calculateParticipantCouple(
            String debtorAgent,
            FundTransferSystem debtorSystem,
            String creditorAgent,
            FundTransferSystem creditorSystem
    ) {
        ParticipantCouple result = new ParticipantCouple();

        if (debtorSystem == null) {
            debtorSystem = FundTransferSystem.BOTH;
        }

        if (creditorSystem == null) {
            debtorSystem = FundTransferSystem.BOTH;
        }

        if (debtorSystem == FundTransferSystem.ACH) {
            if (debtorAgent != null && debtorAgent.length() > 0) {
                result.debtorAgent = debtorAgent;
            } else {
                result.debtorAgent = "%VNVN";
            }
        } else if (debtorSystem == FundTransferSystem.IBFT) {
            if (debtorAgent != null && debtorAgent.length() > 0) {
                result.debtorAgent = debtorAgent.substring(0, 4) + "VNVP";
            } else {
                result.debtorAgent = "%VNVP";
            }
        } else {
            if (debtorAgent != null && debtorAgent.length() > 0) {
                result.debtorAgent = debtorAgent.substring(0, 4) + "%";
            } else {
                result.debtorAgent = "%%";
            }
        }

        if (creditorSystem == FundTransferSystem.ACH) {
            if (creditorAgent != null && creditorAgent.length() > 0) {
                result.creditorAgent = creditorAgent;
            } else {
                result.creditorAgent = "%VNVN";
            }
        } else if (creditorSystem == FundTransferSystem.IBFT) {
            if (creditorAgent != null && creditorAgent.length() > 0) {
                result.creditorAgent = creditorAgent.substring(0, 4) + "VNVP";
            } else {
                result.creditorAgent = "%VNVP";
            }
        } else {
            if (creditorAgent != null && creditorAgent.length() > 0) {
                result.creditorAgent = creditorAgent.substring(0, 4) + "VNV%";
            } else {
                result.creditorAgent = "%%";
            }
        }

        return result;
    }
    
    public static ParticipantCouple calculateParticipantCoupleRegex(
            String debtorAgent,
            FundTransferSystem debtorSystem,
            String creditorAgent,
            FundTransferSystem creditorSystem
    ) {
        ParticipantCouple result = new ParticipantCouple();

        if (debtorSystem == null) {
            debtorSystem = FundTransferSystem.BOTH;
        }

        if (creditorSystem == null) {
            debtorSystem = FundTransferSystem.BOTH;
        }

        if (debtorSystem == FundTransferSystem.ACH) {
            if (debtorAgent != null && debtorAgent.length() > 0) {
                result.debtorAgent = debtorAgent;
            } else {
                result.debtorAgent = "VNVN$";
            }
        } else if (debtorSystem == FundTransferSystem.IBFT) {
            if (debtorAgent != null && debtorAgent.length() > 0) {
                result.debtorAgent = debtorAgent.substring(0, 4) + "VNVP";
            } else {
                result.debtorAgent = "VNVP$";
            }
        } else {
            if (debtorAgent != null && debtorAgent.length() > 0) {
                result.debtorAgent = "^" + debtorAgent.substring(0, 4);
            } else {
                result.debtorAgent = ".*";
            }
        }

        if (creditorSystem == FundTransferSystem.ACH) {
            if (creditorAgent != null && creditorAgent.length() > 0) {
                result.creditorAgent = creditorAgent;
            } else {
                result.creditorAgent = "VNVN$";
            }
        } else if (creditorSystem == FundTransferSystem.IBFT) {
            if (creditorAgent != null && creditorAgent.length() > 0) {
                result.creditorAgent = creditorAgent.substring(0, 4) + "VNVP";
            } else {
                result.creditorAgent = "VNVP$";
            }
        } else {
            if (creditorAgent != null && creditorAgent.length() > 0) {
                result.creditorAgent = "^" + creditorAgent.substring(0, 4) + "VNV.*";
            } else {
                result.creditorAgent = ".*";
            }
        }

        return result;
    }
}
