/*
 *	MSS Code Factory CFLib DbUtil
 *
 *	Copyright (c) 2025 Mark Stephen Sobkow
 *
 *	This file is part of MSS Code Factory 3.0.
 *
 *	MSS Code Factory 3.0 is free software: you can redistribute it and/or modify
 *	it under the terms of the Apache v2.0 License as published by the Apache Foundation.
 *
 *	MSS Code Factory 3.0 is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 *	You should have received a copy of the Apache v2.0 License along with
 *	MSS Code Factory.  If not, see https://www.apache.org/licenses/LICENSE-2.0
 *
 *	Contact Mark Stephen Sobkow at mark.sobkow@gmail.com for commercial licensing or
 *  customization.
 */

import server.markhome.msscf.msscf.cflib.dbutil.CFLibUuid6;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CFLibUuid6Converter implements AttributeConverter<CFLibUuid6, byte[]> {
    
    @Override
    public byte[] convertToDatabaseColumn(CFLibUuid6 attribute) {
        return attribute != null ? attribute.getBytes() : null;
    }

    @Override
    public CFLibUuid6 convertToEntityAttribute(byte[] dbData) {
        return dbData != null ? new CFLibUuid6(dbData) : null;
    }
}
