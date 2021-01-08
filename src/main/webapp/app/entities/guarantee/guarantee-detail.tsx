import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './guarantee.reducer';
import { IGuarantee } from 'app/shared/model/guarantee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGuaranteeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GuaranteeDetail = (props: IGuaranteeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { guaranteeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="tinhocdanangApp.guarantee.detail.title">Guarantee</Translate> [<b>{guaranteeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="description">
              <Translate contentKey="tinhocdanangApp.guarantee.description">Description</Translate>
            </span>
          </dt>
          <dd>{guaranteeEntity.description}</dd>
          <dt>
            <span id="creationDate">
              <Translate contentKey="tinhocdanangApp.guarantee.creationDate">Creation Date</Translate>
            </span>
          </dt>
          <dd>
            {guaranteeEntity.creationDate ? (
              <TextFormat value={guaranteeEntity.creationDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/guarantee" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/guarantee/${guaranteeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ guarantee }: IRootState) => ({
  guaranteeEntity: guarantee.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GuaranteeDetail);
